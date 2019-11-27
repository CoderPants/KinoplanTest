package kinoplan.testapp.spacexscheduler.models

import android.app.Application
import androidx.lifecycle.*
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.storage.Repository

class LaunchActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository.getInstance(application)

    //Really bad
    var callBackFromViewModel : VMCallBack? = null

    //Really bad
    private val callBackFromRepository : Repository.RepositoryCallBack = object : Repository.RepositoryCallBack {
        override fun noBooksAddedCondition() {
            callBackFromViewModel!!.noBooksAddedCondition()
        }
    }
    //Really bad
    init {
        repository.callBack = callBackFromRepository
    }

    //For animation checking
    var isFirstStageLayoutCollapsed : Boolean = true
    var isSecondStageLayoutCollapsed : Boolean = true
    var isDetailsLayoutCollapsed : Boolean = true

    fun getLaunches() : LiveData<List<Launch>> = repository.getLaunches()

    fun getLaunchById(id : Int) : LiveData<Launch> = repository.getLaunchById(id)

    fun getDataFromServer() = repository.sendRequestToServer()

    //Really bad
    interface VMCallBack{
        fun noBooksAddedCondition()
    }
}