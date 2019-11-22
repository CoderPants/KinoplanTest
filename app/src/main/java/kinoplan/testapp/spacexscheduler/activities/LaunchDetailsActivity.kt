package kinoplan.testapp.spacexscheduler.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.databinding.ActivityLaunchDetailsBinding
import kinoplan.testapp.spacexscheduler.databinding.RocketCollapsingLayoutBinding
import kinoplan.testapp.spacexscheduler.models.LaunchActivityViewModel
import kinoplan.testapp.spacexscheduler.pojos.Launch

class LaunchDetailsActivity : BaseActivity() {

    lateinit var binding : ActivityLaunchDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_details)
        getRidOfTopBar()

        observeLiveData()

    }

    private fun observeLiveData() {
        val model : LaunchActivityViewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)
        val launchID : Int = intent.getIntExtra(IntentKeys.FLIGHT_ID, -1)

        if(launchID == -1) {
            onBackPressed()
            Toast.makeText(applicationContext, "Something wrong with data in database! Contact us", Toast.LENGTH_SHORT).show()
            return
        }

        model.getLaunchById(launchID).observe(this, Observer<Launch> {launch ->
            binding.launch = launch
            binding.links = launch.links
            binding.rocket = launch.rocket
            binding.firstStage = launch.rocket.firstStage
            binding.secondStage = launch.rocket.secondStage
            Log.i(ConstantsForApp.LOG_TAG, "${launch.links}")
        })
    }
}
