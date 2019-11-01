package kinoplan.testapp.spacexscheduler.request

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("/v3/launches")
    fun getAllLaunches(@Query("sort") sortCase : String) : Call<JsonArray>
}