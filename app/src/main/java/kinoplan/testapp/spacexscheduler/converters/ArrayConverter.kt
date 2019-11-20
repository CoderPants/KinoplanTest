package kinoplan.testapp.spacexscheduler.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.FirstStage
import java.lang.StringBuilder
import java.lang.reflect.Type

class ArrayConverter {

    @TypeConverter
    fun fromString(value: String): ArrayList<String>? {
        var result : ArrayList<String>? = null
        val values : List<String> = value.split(" ")

        //'Cos even empty string has size of 1
        if(values.size > 1) {
            result = ArrayList()
            result.addAll(values)
        }

        return result
    }

    @TypeConverter
    fun fromJsonObject(images: ArrayList<String>?): String {
        if(images == null)
            return ""

        val result = StringBuilder("")

        for(image in images)
            result.append("$image ")

        return result.toString().trim()
    }
}