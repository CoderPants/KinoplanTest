package kinoplan.testapp.spacexscheduler.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.Rocket
import java.lang.reflect.Type

class RocketConverter {
    @TypeConverter
    fun fromString(value: String): Rocket {
        val type: Type = object : TypeToken<Rocket>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromJsonObject(rocket : Rocket): String = Gson().toJson(rocket)
}