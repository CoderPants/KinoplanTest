package kinoplan.testapp.spacexscheduler.storage

import com.google.gson.JsonArray
import kinoplan.testapp.spacexscheduler.parsers.LaunchParser
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DataBaseHelper {

    private val parser: LaunchParser = LaunchParser()

    fun getNewLaunches(launchesDB: List<Launch>, launchesServer: List<Launch>): List<Launch> {

        if (launchesDB.isEmpty())
            return launchesServer

        if (launchesDB == launchesServer)
            return emptyList()

        val result: ArrayList<Launch> = ArrayList()

        for (launch in launchesServer)
            if (!launchesDB.contains(launch))
                result.add(launch)

        return result
    }

    suspend fun parseLaunches(receivedLaunches: JsonArray): List<Launch> =
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            parser.parseFromJsonArray(jsonArray = receivedLaunches)
        }
}