package kinoplan.testapp.spacexscheduler.storage

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.JsonArray
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.dao.LaunchDao
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.request.RequestSender
import kotlinx.coroutines.*

class Repository private constructor(application: Application) {

    //Really bad!
    var callBack : RepositoryCallBack ? = null

    private val requestSender : RequestSender = RequestSender()

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

    private fun insertLaunches(launches : List<Launch>) = CoroutineScope(Dispatchers.IO).launch { launchDao.insertLaunches(launches) }

    private suspend fun getLaunchesAsync() : List<Launch> = launchDao.getLaunchesAsync()

    //Connection to the server logic
    fun sendRequestToServer(){
        CoroutineScope(Dispatchers.IO).launch {
            val receivedLaunches = requestSender.sendGetLaunchesRequest(ConstantsForApp.SORT_CASE, ConstantsForApp.ORDER_CASE)
            insertDataFromServer(receivedLaunches)
        }
    }

    private fun insertDataFromServer(receivedLaunches : JsonArray){
        CoroutineScope(Dispatchers.Default).launch{

            val launchesFromServer : List<Launch> = DataBaseHelper.parseLaunches(receivedLaunches)
            val launchesFromDataBase : List<Launch> = getLaunchesAsync()

            val newLaunches : List<Launch> = DataBaseHelper.getNewLaunches(launchesFromDataBase, launchesFromServer)

            if(newLaunches.isNotEmpty())
                insertLaunches(launchesFromServer)
            else {
                Log.i(ConstantsForApp.LOG_TAG, "Data in base is up to date!")
                callBack!!.noBooksAddedCondition()
            }
        }
    }


    //Really bad
    interface RepositoryCallBack{
        fun noBooksAddedCondition()
    }

}