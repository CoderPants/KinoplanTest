package kinoplan.testapp.spacexscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRidOfTopBar()
        goToLaunchActivity(findViewById(R.id.greetings_activity_btn))
    }

    private fun goToLaunchActivity(view : View) =
        view.setOnClickListener {
            startActivity(Intent(this, LaunchesActivity::class.java))
        }

}
