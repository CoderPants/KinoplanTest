package kinoplan.testapp.spacexscheduler.activities

import android.util.Log
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.models.LaunchActivityViewModel
import kinoplan.testapp.spacexscheduler.pojos.Launch

class LaunchesActivity : BaseActivity() {

    lateinit var viewModel : LaunchActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        getRidOfTopBar()

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)

        viewModel.getLaunches().observe(this,
            Observer<List<Launch>> { launches ->

                print(launches)
                if(launches.isEmpty())
                    viewModel.getDataFromServer()

            })
    }
}
