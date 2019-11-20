package kinoplan.testapp.spacexscheduler.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.Rocket
import kinoplan.testapp.spacexscheduler.pojos.SecondStage
import java.lang.reflect.Type

class SecondStageConverter {

    @TypeConverter
    fun fromString(value: String): SecondStage {
        val type: Type = object : TypeToken<SecondStage>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromJsonObject(secondStage: SecondStage): String = Gson().toJson(secondStage)
}