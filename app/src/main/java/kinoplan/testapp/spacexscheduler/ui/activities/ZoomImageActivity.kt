package kinoplan.testapp.spacexscheduler.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.ui.fragments.ZoomImageFragment
import kotlin.math.abs

class ZoomImageActivity : BaseActivity() {

    private lateinit var images : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_zoom_image)
        getRidOfTopBar()

        val imagesString : String? = intent.getStringExtra(IntentKeys.IMAGES)
        val position : Int  = intent.getIntExtra(IntentKeys.IMAGE_POSITION, -1)

        if(imagesString == null || position == -1) {
            Toast.makeText(this, "Oops! Something went wrong. Contact us!", Toast.LENGTH_SHORT).show()
            finish()
        }

        images = imagesString!!.split(" ")

        createViewPager(position)

        setOnBackPressedLogic()
    }

    private fun setOnBackPressedLogic() {
        val backPressed : ImageView = findViewById(R.id.zoom_activity_go_back_btn)
        backPressed.setOnClickListener { onBackPressed() }
    }

    private fun createViewPager(position: Int) {
        val viewPager : ViewPager = findViewById(R.id.zoom_activity_view_pager)
        val pagerAdapter : PagerAdapterImpl? = PagerAdapterImpl(supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = position

        //Opacity for animation
        viewPager.setPageTransformer(false) { v: View, pos: Float ->
            val opacity = abs(abs(pos) - 1)
            v.alpha = opacity
        }

    }

    inner class PagerAdapterImpl(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

        override fun getItem(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putString(IntentKeys.IMAGE_URL, images[position])

            val fragment = ZoomImageFragment()
            fragment.arguments = bundle

            return fragment
        }

        override fun getCount(): Int =  images.size

    }
}
