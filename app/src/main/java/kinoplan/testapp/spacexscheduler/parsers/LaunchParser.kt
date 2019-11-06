package kinoplan.testapp.spacexscheduler.parsers

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LaunchParser {

    fun parseFromJsonArray(jsonArray: JsonArray) : List<Launch>{

        val result = ArrayList<Launch>()

        CoroutineScope(Dispatchers.IO).launch {
            for (element in jsonArray)
                result.add(getLaunch(element.asJsonObject))

        }
        return result
    }

    private fun getLaunch(jsonObject: JsonObject) : Launch {
        //Only this can be JsonNull
        val launchSuccessElem : JsonElement = jsonObject.get("launch_success")
        val detailsElem : JsonElement = jsonObject.get("details")

        val links : JsonObject = jsonObject.getAsJsonObject("links")
        val imageElem : JsonElement = links.get("mission_patch")
        val smallImageElem : JsonElement = links.get("mission_patch_small")

        return Launch(
            jsonObject.get("flight_number").asInt,
            jsonObject.get("mission_name").asString,
            links,
            jsonObject.get("launch_date_utc").asString,
            if(launchSuccessElem.isJsonNull) null else launchSuccessElem.asBoolean,
            if(detailsElem.isJsonNull) null else detailsElem.asString,
            if(imageElem.isJsonNull) null else imageElem.asString,
            if(smallImageElem.isJsonNull) null else smallImageElem.asString
        )
    }
}