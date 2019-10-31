package kinoplan.testapp.spacexscheduler.dataFromServer

import com.google.gson.JsonArray

data class Mission (
    private val flight_number : Int,
    private val mission_name : String,
    private val mission_id : JsonArray

)