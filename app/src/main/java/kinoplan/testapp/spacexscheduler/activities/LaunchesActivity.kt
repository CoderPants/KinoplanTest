package kinoplan.testapp.spacexscheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LaunchesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        getRidOfTopBar()
    }
}