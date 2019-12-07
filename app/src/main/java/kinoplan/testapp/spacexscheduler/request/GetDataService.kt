package kinoplan.testapp.spacexscheduler.request

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("/v3/launches")
    suspend fun getAllLaunches(@Query("sort") sortCase : String, @Query("order") orderCase : String) : Response<JsonArray>
}