package kinoplan.testapp.spacexscheduler.storage

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.dao.LaunchDao
import kinoplan.testapp.spacexscheduler.parsers.LaunchParser
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.request.RequestSender
import kotlinx.coroutines.*

class Repository private constructor(application: Application) {

    private val parser : LaunchParser = LaunchParser()

    //Really bad!
    var callBack : RepositoryCallBack ? = null

    private val requestSender : RequestSender = RequestSender(object : RequestSender.RequestCallBack
    {
        override fun onGetLaunchesResponse(receivedLaunches: JsonArray) {
            CoroutineScope(Dispatchers.Default).launch {
                val launches : List<Launch> = parser.parseFromJsonArray(jsonArray = receivedLaunches)
                val launchesFromDataBase : List<Launch> = getLaunchesAsync()

                if(launches != launchesFromDataBase)
                    insertLaunchesAsync(launches)
                else {
                    Log.i(ConstantsForApp.LOG_TAG, "Data in base is up to date!")
                    callBack!!.noBooksAddedCondition()
                }
            }
        }
    })

    private val launchDao : LaunchDao

    init {
        val dataBase = DataBase.getDataBase(application)
        launchDao = dataBase!!.launchDao()
    }

    companion object {

        private var repository: Repository? = null

        fun getInstance(application: Application): Repository {
            if (repository == null)
                synchronized(Repository::class.java) {
                    if (repository == null)
                        repository = Repository(application)

                }
            return repository!!
        }
    }

    fun getLaunches() : LiveData< List<Launch> > = launchDao.getLaunches()

    fun getLaunchById(id : Int) : LiveData<Launch> = launchDao.getLaunchById(id)

    fun insertLaunchesAsync(launches : List<Launch>) = CoroutineScope(Dispatchers.IO).async { launchDao.insertLaunches(launches) }

    fun sendRequestToServer(){
        requestSender.sendGetLaunchesRequest(ConstantsForApp.SORT_CASE, ConstantsForApp.ORDER_CASE)
    }

    suspend fun getLaunchesAsync() : List<Launch> = launchDao.getLaunchesAsync()


    //Really bad
    interface RepositoryCallBack{
        fun noBooksAddedCondition()
    }

}