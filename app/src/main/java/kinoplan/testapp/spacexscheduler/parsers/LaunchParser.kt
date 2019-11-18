package kinoplan.testapp.spacexscheduler.parsers

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kinoplan.testapp.spacexscheduler.pojos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder

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

        val linksObject : JsonObject = jsonObject.getAsJsonObject("links")
        val imageElem : JsonElement = linksObject.get("mission_patch")
        val smallImageElem : JsonElement = linksObject.get("mission_patch_small")

        return Launch(
            flight_number = jsonObject.get("flight_number").asInt,
            mission_name = jsonObject.get("mission_name").asString,
            launch_date_utc = jsonObject.get("launch_date_utc").asString,
            launch_success = if(launchSuccessElem.isJsonNull) null else launchSuccessElem.asBoolean,
            details = if(detailsElem.isJsonNull) null else detailsElem.asString,
            image = if(imageElem.isJsonNull) null else imageElem.asString,
            smallImage = if(smallImageElem.isJsonNull) null else smallImageElem.asString,
            links = getLinks(linksObject),
            rocket = getRocket(jsonObject.getAsJsonObject("rocket"))
        )
    }

    private fun getRocket(jsonObject: JsonObject) : Rocket =
        Rocket(
        name = jsonObject.get("rocket_name").asString,
        type = jsonObject.get("rocket_type").asString,
        firstStage = getFirstStageOfRocket(jsonObject.get("first_stage").asJsonObject),
        secondStage = getSecondStageOfRocket(jsonObject.get("second_stage").asJsonObject)
    )

    private fun getFirstStageOfRocket(firstStage: JsonObject): FirstStage {

        val firstCore : JsonObject = firstStage.getAsJsonArray("cores")[0].asJsonObject

        val coreSerial : JsonElement = firstCore.get("core_serial")
        val gridFins : JsonElement = firstCore.get("gridfins")
        val legs : JsonElement = firstCore.get("legs")
        val landingPlace : JsonElement = firstCore.get("landing_type")

        return FirstStage(
            coreSerial = if(coreSerial.isJsonNull) null else coreSerial.asString,
            gridFins = if (gridFins.isJsonNull) null else gridFins.asBoolean,
            legs = if(legs.isJsonNull) null else legs.asBoolean,
            landingPlace = if(landingPlace.isJsonNull) null else landingPlace.asString
        )
    }


    private fun getSecondStageOfRocket(secondStage: JsonObject): SecondStage {
        val firstPayload : JsonObject = secondStage.getAsJsonArray("payloads")[0].asJsonObject

        val customersArray : JsonArray = firstPayload.getAsJsonArray("customers")
        val customers = StringBuilder("")

        for (customer in customersArray)
            customers.append("$customer ")

        return SecondStage(
            nationality = firstPayload.get("nationality").asString,
            manufacturer = firstPayload.get("manufacturer").asString,
            payloadType = firstPayload.get("payload_type").asString,
            customers = customers.toString(),
            orbit = firstPayload.get("orbit").asString

        )
    }

    private fun getLinks(links : JsonObject) : Links{

        val wiki : JsonElement = links.get("wikipedia")
        val reddit : JsonElement = links.get("reddit_media")
        val article : JsonElement = links.get("article_link")
        val video : JsonElement = links.get("video_link")

        return Links(
            wikipedia = if(wiki.isJsonNull) null else wiki.asString,
            reddit = if(reddit.isJsonNull) null else reddit.asString,
            article = if(article.isJsonNull) null else article.asString,
            video = if(video.isJsonNull) null else video.asString
        )
    }
}