package kinoplan.testapp.spacexscheduler.request

import android.util.Log
import com.google.gson.JsonArray
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestSender (private val callBack: RequestCallBack) {

    fun sendGetLaunchesRequest(sortCase : String, orderCase : String){

        val service = RetrofitInstance.instance.create(GetDataService::class.java)

        val getLaunchesCall = service.getAllLaunches(sortCase, orderCase)

        getLaunchesCall.enqueue(object : Callback<JsonArray> {

            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                val serverResponse : JsonArray = response.body() ?: JsonArray()

                callBack.onGetLaunchesResponse(serverResponse)
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.e(ConstantsForApp.LOG_TAG,
                    "Error by getting data from server. " +
                            "Call itself ${call.request().body()}. " +
                            "Error itself: ", t)
            }

        })
    }


    interface RequestCallBack {
        fun onGetLaunchesResponse(receivedLaunches : JsonArray)
    }
}