package kinoplan.testapp.spacexscheduler.ui.activities

import android.content.res.Configuration
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kinoplan.testapp.spacexscheduler.R

abstract class BaseActivity : AppCompatActivity() {

    protected fun getRidOfTopBar() {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    protected fun checkOrientationOfPhone(view : View) {
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
            view.setBackgroundResource(R.drawable.main_activity_background_landscape)
        else
            view.setBackgroundResource(R.drawable.main_activity_background_portrait)

    }
}