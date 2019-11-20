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

    private val requestSender : RequestSender = RequestSender(object : RequestSender.RequestCallBack
    {
        override fun onGetLaunchesResponse(receivedLaunches: JsonArray) {

            CoroutineScope(Dispatchers.Default).launch {
                val launches : List<Launch> = parser.parseFromJsonArray(jsonArray = receivedLaunches)
                Log.i(ConstantsForApp.LOG_TAG, "Launches itself SIZE ${launches.size} RESPONSE SIZE ${receivedLaunches.size()}")
                insertLaunchesAsync(launches)
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

    fun insertLaunchesAsync(launches : List<Launch>) = CoroutineScope(Dispatchers.IO).async { launchDao.insertLaunches(launches) }

    fun sendRequestToServer(){
        requestSender.sendGetLaunchesRequest(ConstantsForApp.SORT_CASE, ConstantsForApp.ORDER_CASE)
    }

}