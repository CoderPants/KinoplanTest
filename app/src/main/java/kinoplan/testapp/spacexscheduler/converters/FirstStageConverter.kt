package kinoplan.testapp.spacexscheduler.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.FirstStage
import kinoplan.testapp.spacexscheduler.pojos.Rocket
import java.lang.reflect.Type

class FirstStageConverter {

    @TypeConverter
    fun fromString(value: String): FirstStage {
        val type: Type = object : TypeToken<FirstStage>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromJsonObject(firstStage: FirstStage): String = Gson().toJson(firstStage)
}