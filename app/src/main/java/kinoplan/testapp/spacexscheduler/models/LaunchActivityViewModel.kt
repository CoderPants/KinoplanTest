package kinoplan.testapp.spacexscheduler.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
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

    //For animation checking
    var isFirstStageLayoutCollapsed : Boolean = true
    var isSecondStageLayoutCollapsed : Boolean = true
    var isDetailsLayoutCollapsed : Boolean = true

    fun getLaunches() : LiveData<List<Launch>> = repository.getLaunches()

    fun getLaunchById(id : Int) : LiveData<Launch> = repository.getLaunchById(id)

    fun getDataFromServer() = repository.sendRequestToServer()

    fun createCallBackFromRepository() {
        repository.callBack = callBackFromRepository
    }

    //Really bad
    interface VMCallBack{
        fun noBooksAddedCondition()
    }
}