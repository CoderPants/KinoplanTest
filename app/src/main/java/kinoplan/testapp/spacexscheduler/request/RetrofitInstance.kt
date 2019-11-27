package kinoplan.testapp.spacexscheduler.request

import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val RETROFIT : Retrofit = Retrofit.Builder()
        .baseUrl(ConstantsForApp.BASE_URL_RETROFIT)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}