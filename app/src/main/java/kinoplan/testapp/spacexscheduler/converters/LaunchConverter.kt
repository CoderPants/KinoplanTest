package kinoplan.testapp.spacexscheduler.converters

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp

class LaunchConverter {
    @TypeConverter
    fun fromString(value: String): JsonObject {
        return if (value == "") JsonObject() else JsonParser().parse(value).asJsonObject
    }

    @TypeConverter
    fun fromJsonObject(value: JsonObject?): String {
        return try {
            value?.toString() ?: ""
        } catch (e: Exception) {
            Log.e(
                ConstantsForApp.LOG_TAG,
                "Exception caused by converting server response to json array. Exception ",
                e
            )
            ""
        }

    }
}