package kinoplan.testapp.spacexscheduler.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kinoplan.testapp.spacexscheduler.R

class LaunchDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_details)
        getRidOfTopBar()


    }
}
