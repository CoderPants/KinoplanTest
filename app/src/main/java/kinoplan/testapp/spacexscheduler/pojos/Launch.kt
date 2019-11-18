package kinoplan.testapp.spacexscheduler.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject

@Entity(tableName = "launch_description")
data class Launch (
    val flight_number : Int,
    val mission_name : String,
    val launch_date_utc : String,
    val launch_success :  Boolean?,
    val details : String?,
    val image : String?,
    val smallImage : String?,
    val links : Links,
    val rocket: Rocket)
{
    @PrimaryKey(autoGenerate = true)
    var key : Int = 0

    //Format of date
    // year-month-dayThours:min:sec.000Z
    fun getDate() : String{
        val dateAndTimePair : List<String> = launch_date_utc.split("T")
        val time = dateAndTimePair[1]
        return dateAndTimePair[0] +
                "\n ${time.substring(0, time.lastIndexOf('.'))}"
    }

    override fun toString(): String {
        return "Launch(flight_number=$flight_number, " +
                "mission_name='$mission_name', " +
                "launch_date_utc='$launch_date_utc', " +
                "launch_success=$launch_success, " +
                "details=$details, " +
                "image=$image, " +
                "smallImage=$smallImage, " +
                "links=$links, " +
                "rocket=$rocket, " +
                "key=$key)"
    }


}