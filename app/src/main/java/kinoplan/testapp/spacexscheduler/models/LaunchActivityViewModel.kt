package kinoplan.testapp.spacexscheduler.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.storage.Repository

class LaunchActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(application)

    fun getLaunches() : LiveData<List<Launch>> = repository.getLaunches()

    fun getDataFromServer() = repository.sendRequestToServer()
}