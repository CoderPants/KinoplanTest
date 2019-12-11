package kinoplan.testapp.spacexscheduler.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.models.LaunchActivityViewModel
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kinoplan.testapp.spacexscheduler.ui.adapters.LaunchesAdapter
import kinoplan.testapp.spacexscheduler.ui.customviews.OverlayView


class MainActivity : BaseActivity(), LifecycleObserver {

    private lateinit var viewModel : LaunchActivityViewModel

    private lateinit var recyclerView : RecyclerView
    //private var recyclerViewState : Parcelable? = null

    private val adapter : LaunchesAdapter = LaunchesAdapter()

    private lateinit var loadingIndicator : OverlayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getRidOfTopBar()
        checkOrientationOfPhone(findViewById(R.id.main_activity_background_carrying_view))

        viewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)
        viewModel.createCallBackFromRepository()

        recyclerView = findViewById(R.id.launches_activity_rv_launches)

        loadingIndicator = findViewById(R.id.launches_activity_loading_indicator_container)

        observeLaunches()
        observeLoadingIndicator()
        viewModel.setLoadingVisibility(true)

        recyclerViewCreation()

        //recyclerViewState = savedInstanceState?.getParcelable(ConstantsForApp.SCROLL_POSITION)

        setTopBarLogic(findViewById(R.id.main_activity_refresh_btn))

    }

    private fun observeLoadingIndicator() {
        viewModel.loadingLiveData.observe(this, Observer<Boolean>{visible ->
            if(visible)
                loadingIndicator.visibility = View.VISIBLE
            else
                loadingIndicator.visibility = View.GONE
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //outState.putParcelable(ConstantsForApp.SCROLL_POSITION, recyclerView.layoutManager?.onSaveInstanceState())
        Log.i(ConstantsForApp.LOG_TAG, "Layout ${((recyclerView.layoutManager)as LinearLayoutManager).findFirstVisibleItemPosition()}")
        recyclerView.computeHorizontalScrollOffset()
        recyclerView.computeVerticalScrollOffset()
        Log.i(ConstantsForApp.LOG_TAG, " Horizontal ${recyclerView.computeHorizontalScrollOffset()}")
        Log.i(ConstantsForApp.LOG_TAG, " Vertical ${recyclerView.computeVerticalScrollOffset()}")
    }

    private fun observeLaunches() {
        viewModel.getLaunches().observe(this,
            Observer<List<Launch>> { launches ->

                if(launches.isEmpty())
                    viewModel.getDataFromServer()
                else
                {
                    viewModel.setLoadingVisibility(false)
                    adapter.launches = launches
                    //recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                }

            })
    }

    private fun recyclerViewCreation(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
       /* recyclerView.viewTreeObserver
            .addOnGlobalLayoutListener {
                recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }*/
    }

    private fun setTopBarLogic( refreshBtn : View){
        refreshBtn.setOnClickListener {
            loadingIndicator.visibility = View.VISIBLE
            viewModel.getDataFromServer() }
    }

}
