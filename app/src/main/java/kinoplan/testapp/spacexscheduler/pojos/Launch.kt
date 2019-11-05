package kinoplan.testapp.spacexscheduler.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject

@Entity(tableName = "launch_description")
data class Launch (
    @PrimaryKey
    val flight_number : Int,
    val mission_name : String,
    val links : JsonObject,
    val launch_date_utc : String,
    val launch_success :  Boolean?,
    val details : String?,
    val image : String?,
    val smallImage : String?)
{
    //Auto generated
    override fun toString(): String {
        return "Launch(flight_number=$flight_number," +
                " mission_name='$mission_name', " +
                "links=$links, " +
                "launch_date_utc='$launch_date_utc', " +
                "launch_success=$launch_success," +
                " details='$details')"
    }

}