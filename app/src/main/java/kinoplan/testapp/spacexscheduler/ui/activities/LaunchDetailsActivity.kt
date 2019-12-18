package kinoplan.testapp.spacexscheduler.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.ui.adapters.ImagesAdapter
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.databinding.ActivityLaunchDetailsBinding
import kinoplan.testapp.spacexscheduler.models.LaunchActivityViewModel
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.pojos.Links
import kinoplan.testapp.spacexscheduler.ui.animation.AnimationHelper
import kinoplan.testapp.spacexscheduler.ui.binding.BindingLinksHandler
import kinoplan.testapp.spacexscheduler.ui.customviews.OverlayView
import java.util.ArrayList

class LaunchDetailsActivity : BaseActivity() {

    private lateinit var binding : ActivityLaunchDetailsBinding
    private lateinit var adapterRV : ImagesAdapter
    private lateinit var viewModel: LaunchActivityViewModel
    private lateinit var loadingIndicator : OverlayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getRidOfTopBar()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_details)
        adapterRV = ImagesAdapter()

        viewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)

        createRecyclerView()
        observeLaunches()

        loadingIndicator = binding.launchesDetailsActivityLoadingIndicatorContainer
        observeLoading()
        viewModel.setLoadingVisibility(true)

        createAnimation()

        setOnBackPressedLogic()

    }

    private fun observeLoading() {
        viewModel.loadingLiveData.observe(this, Observer<Boolean>{visible ->
            if(visible)
                loadingIndicator.visibility = View.VISIBLE
            else
                loadingIndicator.visibility = View.GONE
        })
    }

    private fun setOnBackPressedLogic() =
        binding.activityLaunchDetailsGoBackBtn.setOnClickListener { onBackPressed() }


    private fun observeLaunches() {
        val launchID : Int = intent.getIntExtra(IntentKeys.FLIGHT_ID, -1)

        if(launchID == -1) {
            onBackPressed()
            Toast.makeText(applicationContext, "Something wrong with data in database! Contact us", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.getLaunchById(launchID).observe(this, Observer<Launch> { launch ->

            val links : Links = launch.links

            bindLinks(links)
            bindImages(links.images)

            binding.launch = launch
            binding.rocket = launch.rocket
            binding.firstStage = launch.rocket.firstStage
            binding.secondStage = launch.rocket.secondStage
            //For click handling
            binding.handler = BindingLinksHandler()

            viewModel.setLoadingVisibility(false)
        })
    }

    private fun bindImages(images: ArrayList<String>) {
         if(images.size > 0)
             adapterRV.images = images
        else
             binding.activityLaunchDetailsImagesTopText.visibility = View.GONE
    }

    private fun bindLinks(links: Links) {
        if(links.article != null || links.reddit != null || links.video != null || links.wikipedia != null)
            binding.links = links
        else
            binding.activityLaunchDetailsLinksTopText.visibility = View.GONE
    }

    private fun createRecyclerView() {
        val recyclerView : RecyclerView = binding.activityLaunchDetailsImagesContainer
        recyclerView.adapter = adapterRV
    }


    private fun createAnimation() {
        val rocketDetailsBinding = binding.activityLaunchDetailsRocketCollapsingContainer

        val firstStageLayout : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsFirstStageContainer
        val firstStageLayoutCollapsing : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsFirstStageDetailsContainer
        val firstStageImage : ImageView = rocketDetailsBinding.activityLaunchDetailsFirstStageContainerTopImage

        val secondStageLayout : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsSecondStageContainer
        val secondStageLayoutCollapsing : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsSecondStageDetailsContainer
        val secondStageImage : ImageView = rocketDetailsBinding.activityLaunchDetailsSecondStageContainerTopImage

        val detailsLayout : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsDetailsContainer
        val detailsLayoutCollapsing : ConstraintLayout = rocketDetailsBinding.activityLaunchDetailsDetailsForDetailsContainer
        val detailsImage : ImageView = rocketDetailsBinding.activityLaunchDetailsDetailsTopImage

        checkIfCollapsed(firstStageLayoutCollapsing, firstStageImage, viewModel.isFirstStageLayoutCollapsed)
        checkIfCollapsed(secondStageLayoutCollapsing, secondStageImage, viewModel.isSecondStageLayoutCollapsed)
        checkIfCollapsed(detailsLayoutCollapsing, detailsImage, viewModel.isDetailsLayoutCollapsed)

        createClickListenersForAnimation(firstStageLayout, firstStageLayoutCollapsing, firstStageImage,
            secondStageLayout, secondStageLayoutCollapsing, secondStageImage,
            detailsLayout, detailsLayoutCollapsing, detailsImage)
    }

    private fun startAnimation(container : View, isCollapsed : Boolean, image : ImageView) =
        if(isCollapsed) {
            AnimationHelper.expand(container)
            image.setImageDrawable(getDrawable(R.drawable.ic_expand_less_white_30dp))
        }
        else {
            AnimationHelper.collapse(container)
            image.setImageDrawable(getDrawable(R.drawable.ic_expand_more_white_30dp))
        }


    private fun createClickListenersForAnimation(
        firstStageLayout : View,
        firstStageLayoutCollapsing : View,
        firstStageImage : ImageView,
        secondStageLayout : View,
        secondStageLayoutCollapsing : View,
        secondStageImage : ImageView,
        detailsLayout : View,
        detailsLayoutCollapsing : View,
        detailsImage : ImageView
    ) {
        firstStageLayout.setOnClickListener {
            startAnimation(firstStageLayoutCollapsing,  viewModel.isFirstStageLayoutCollapsed, firstStageImage)
            viewModel.isFirstStageLayoutCollapsed = !viewModel.isFirstStageLayoutCollapsed
        }

        secondStageLayout.setOnClickListener {
            startAnimation(secondStageLayoutCollapsing,  viewModel.isSecondStageLayoutCollapsed, secondStageImage)
            viewModel.isSecondStageLayoutCollapsed = !viewModel.isSecondStageLayoutCollapsed
        }

        detailsLayout.setOnClickListener {
            startAnimation(detailsLayoutCollapsing,  viewModel.isDetailsLayoutCollapsed, detailsImage)
            viewModel.isDetailsLayoutCollapsed = !viewModel.isDetailsLayoutCollapsed
        }
    }


    private fun checkIfCollapsed(container : View, image : ImageView, isCollapsed: Boolean){
        if(isCollapsed) {
            container.layoutParams.height = 0
            image.setImageDrawable(getDrawable(R.drawable.ic_expand_more_white_30dp))
        }
        else {
            container.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            image.setImageDrawable(getDrawable(R.drawable.ic_expand_less_white_30dp))
        }
    }



}
