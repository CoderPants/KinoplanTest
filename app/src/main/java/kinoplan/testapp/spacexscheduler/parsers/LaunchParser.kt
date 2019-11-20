package kinoplan.testapp.spacexscheduler.parsers

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kinoplan.testapp.spacexscheduler.pojos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LaunchParser {

    suspend fun parseFromJsonArray(jsonArray: JsonArray) : List<Launch>{

        val result = ArrayList<Launch>()

        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
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
            launch_success = getDataFromJsonElemAsBoolean(launchSuccessElem),
            details = getDataFromJsonElemAsString(detailsElem),
            image = getDataFromJsonElemAsString(imageElem),
            smallImage = getDataFromJsonElemAsString(smallImageElem),
            links = getLinks(linksObject),
            rocket = getRocket( jsonObject.getAsJsonObject("rocket") )
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
            coreSerial = getDataFromJsonElemAsString(coreSerial),
            gridFins = getDataFromJsonElemAsBoolean(gridFins),
            legs = getDataFromJsonElemAsBoolean(legs),
            landingPlace = getDataFromJsonElemAsString(landingPlace)
        )
    }


    private fun getSecondStageOfRocket(secondStage: JsonObject): SecondStage {
        val firstPayload : JsonObject = secondStage.getAsJsonArray("payloads")[0].asJsonObject

        val nationality : JsonElement =  firstPayload.get("nationality")
        val manufacturer : JsonElement = firstPayload.get("manufacturer")
        val payload : JsonElement = firstPayload.get("payload_type")
        val orbit : JsonElement = firstPayload.get("orbit")

        val customersArray : JsonArray = firstPayload.getAsJsonArray("customers")
        val customers : String?

        customers = if(customersArray.size() == 0)
            null
        else {
            val customersBuilder = StringBuilder("")

            for (customer in customersArray)
                customersBuilder.append("$customer ")

            customersBuilder.toString()
        }

        return SecondStage(
            nationality = getDataFromJsonElemAsString(nationality),
            manufacturer = getDataFromJsonElemAsString(manufacturer) ,
            payloadType = getDataFromJsonElemAsString(payload),
            customers = customers,
            orbit = getDataFromJsonElemAsString(orbit)

        )
    }

    private fun getLinks(links : JsonObject) : Links{

        val wiki : JsonElement = links.get("wikipedia")
        val reddit : JsonElement = links.get("reddit_media")
        val article : JsonElement = links.get("article_link")
        val video : JsonElement = links.get("video_link")

        val imagesJson : JsonArray = links.getAsJsonArray("flickr_images")

        /*val images : ArrayList<String>? = if(imagesJson.size() == 0) null else ArrayList()

        if(images != null) {
            for(image in imagesJson)
                images.add(image.asString)
        }*/

        var images : ArrayList<String>? = null

        if(imagesJson.size() != 0) {
            images = ArrayList()

            for(image in imagesJson)
                images.add(image.asString)
        }

        return Links(
            wikipedia = getDataFromJsonElemAsString(wiki),
            reddit = getDataFromJsonElemAsString(reddit),
            article = getDataFromJsonElemAsString(article),
            video = getDataFromJsonElemAsString(video),
            images = images)
    }

    private fun getDataFromJsonElemAsString(jsonElement: JsonElement) : String? = if(jsonElement.isJsonNull) null else jsonElement.asString

    private fun getDataFromJsonElemAsBoolean(jsonElement: JsonElement) : Boolean? = if(jsonElement.isJsonNull) null else jsonElement.asBoolean
}