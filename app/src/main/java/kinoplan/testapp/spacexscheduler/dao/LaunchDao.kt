package kinoplan.testapp.spacexscheduler.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kinoplan.testapp.spacexscheduler.pojos.Launch

@Dao
interface LaunchDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertLaunches(launches : List<Launch>)

    @Query("SELECT * FROM launch_description")
    fun getLaunches() : LiveData< List<Launch> >
}