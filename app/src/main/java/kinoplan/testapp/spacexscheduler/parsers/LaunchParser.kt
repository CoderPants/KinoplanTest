package kinoplan.testapp.spacexscheduler.parsers

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchParser {

    suspend fun parseFromJsonArray(jsonArray: JsonArray) : List<Launch>{

        val result = ArrayList<Launch>()

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {

            for (element in jsonArray) {
                val jsonObject = element.asJsonObject
                try {

                    /*val flight_number_elem : JsonElement = jsonObject.get("flight_number")
                    val mission_name_elem : JsonElement = jsonObject.get("mission_name")
                    val launch_date_utc_elem : JsonElement = jsonObject.get("launch_date_utc")*/
                    val launchSuccessElem : JsonElement = jsonObject.get("launch_success")
                    val detailsElem : JsonElement = jsonObject.get("details")

                    val launch = Launch(
                        jsonObject.get("flight_number").asInt,
                        jsonObject.get("mission_name").asString,
                        jsonObject.getAsJsonObject("links"),
                        jsonObject.get("launch_date_utc").asString,
                        if(launchSuccessElem.isJsonNull) null else launchSuccessElem.asBoolean,
                        if(detailsElem.isJsonNull) null else detailsElem.asString
                    )

                    result.add(launch)
                } catch (e: Exception) {
                    Log.e(ConstantsForApp.LOG_TAG, "Exception", e)
                }
            }
        }

        return result
    }
}