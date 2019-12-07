package kinoplan.testapp.spacexscheduler.request

import android.util.Log
import com.google.gson.JsonArray
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kotlinx.coroutines.*
import retrofit2.Response

class RequestSender {

    suspend fun sendGetLaunchesRequest(sortCase : String, orderCase : String) : JsonArray{

        val service = RetrofitInstance.RETROFIT.create(GetDataService::class.java)
        var result = JsonArray()

        val deferred : Deferred<Unit> = CoroutineScope(Dispatchers.IO).async{
            val response : Response<JsonArray> = service.getAllLaunches(sortCase, orderCase)
            val body : JsonArray? = response.body()

            if(body != null)
                result = body
        }

        checkForException(deferred)

        return result
    }

    private suspend fun checkForException(deferred: Deferred<Unit>){
        try {
            deferred.await()
        } catch (ex: Exception) {
            Log.e(
                ConstantsForApp.LOG_TAG,
                "Error by getting data from server. " +
                        "Error itself: ", ex
            )
        }
    }
}