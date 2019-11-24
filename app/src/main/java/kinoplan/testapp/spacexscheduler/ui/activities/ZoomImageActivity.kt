package kinoplan.testapp.spacexscheduler.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.databinding.ActivityZoomImageBinding

class ZoomImageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRidOfTopBar()

        val binding : ActivityZoomImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_zoom_image)

        val imageUrl : String? = intent.getStringExtra(IntentKeys.IMAGE_URL)

        if(imageUrl == null) {
            Toast.makeText(this, "Oops! Something went wrong. Contact us!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.url = imageUrl

        binding.activityLaunchDetailsGoBackBtn.setOnClickListener { onBackPressed() }

    }
}
