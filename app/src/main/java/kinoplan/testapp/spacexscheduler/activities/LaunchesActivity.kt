package kinoplan.testapp.spacexscheduler.activities

import android.os.Bundle
import kinoplan.testapp.spacexscheduler.R

class LaunchesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        getRidOfTopBar()
    }
}
