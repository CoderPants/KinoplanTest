package kinoplan.testapp.spacexscheduler.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.Links
import java.lang.reflect.Type

class LinksConverter {

    @TypeConverter
    fun fromString(value: String): Links {
        val type: Type = object : TypeToken<Links>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromJsonObject(links : Links): String = Gson().toJson(links)
}