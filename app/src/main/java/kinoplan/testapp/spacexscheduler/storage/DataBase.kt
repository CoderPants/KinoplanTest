package kinoplan.testapp.spacexscheduler.storage

import android.content.Context
import androidx.room.*
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.dao.LaunchDao
import kinoplan.testapp.spacexscheduler.pojos.Launch

@Database(entities = [Launch::class], version = 1)

@TypeConverters(
    RocketConverter::class, LinksConverter::class,
    FirstStageConverter::class, SecondStageConverter::class, ArrayConverter::class)
abstract class DataBase : RoomDatabase() {

    abstract fun launchDao() : LaunchDao

    companion object {

        @Volatile
        private var INSTANCE : DataBase? = null

        @Synchronized
        fun getDataBase(context : Context) : DataBase?{
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    DataBase::class.java, ConstantsForApp.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE
        }
    }
}