package kinoplan.testapp.spacexscheduler.models

import android.app.Application
import androidx.lifecycle.*
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.storage.Repository

class LaunchActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(application)

    //For animation checking
    var isFirstStageLayoutCollapsed : Boolean = true
    var isSecondStageLayoutCollapsed : Boolean = true
    var isDetailsLayoutCollapsed : Boolean = true

    fun getLaunches() : LiveData<List<Launch>> = repository.getLaunches()

    fun getLaunchById(id : Int) : LiveData<Launch> = repository.getLaunchById(id)

    fun getDataFromServer() = repository.sendRequestToServer()
}