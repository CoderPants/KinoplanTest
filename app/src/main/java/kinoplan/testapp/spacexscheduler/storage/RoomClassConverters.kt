package kinoplan.testapp.spacexscheduler.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kinoplan.testapp.spacexscheduler.pojos.FirstStage
import kinoplan.testapp.spacexscheduler.pojos.Links
import kinoplan.testapp.spacexscheduler.pojos.Rocket
import kinoplan.testapp.spacexscheduler.pojos.SecondStage
import java.lang.reflect.Type

open class BaseClassConverter<T> (private val type : Class<T>){

    private val gson : Gson = Gson()

    @TypeConverter
    fun fromString(value: String?): T? = value?.let { gson.fromJson(value, type) }

    @TypeConverter
    fun fromJsonObject(value: T?): String? = value?.let { gson.toJson(value) }
}

open class BaseListConverter<T>(private val type : Type){

    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<T>? = value?.let { gson.fromJson(value, type) }

    @TypeConverter
    fun fromList(list: List<T>?): String? = list?.let { gson.toJson(list) }

}

//For Collections types
private inline fun <reified T> type(): Type = object : TypeToken<T>() {}.type

//Usages
class ArrayConverter : BaseListConverter<ArrayList<String>?>(
    type<ArrayList<String>?>()
)

class LinksConverter : BaseClassConverter<Links>(Links::class.java)

class RocketConverter : BaseClassConverter<Rocket>(Rocket::class.java)

class FirstStageConverter : BaseClassConverter<FirstStage>(FirstStage::class.java)

class SecondStageConverter : BaseClassConverter<SecondStage>(SecondStage::class.java)