package kinoplan.testapp.spacexscheduler.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
        //Really bad
        createCallBack()

        recyclerView = findViewById(R.id.launches_activity_rv_launches)

        loadingIndicator = findViewById(R.id.launches_activity_loading_indicator_container)
        loadingIndicator.visibility = View.VISIBLE

        subscribeToViewModel()
        recyclerViewCreation()

        //recyclerViewState = savedInstanceState?.getParcelable(ConstantsForApp.SCROLL_POSITION)

        setTopBarLogic(findViewById(R.id.main_activity_refresh_btn))

    }

    //Really bad
    private fun createCallBack() {
        val callBackFromViewModel : LaunchActivityViewModel.VMCallBack = object : LaunchActivityViewModel.VMCallBack {
            override fun noBooksAddedCondition() {
               CoroutineScope(Dispatchers.Main).launch {
                   loadingIndicator.visibility = View.GONE
                   Toast.makeText(this@MainActivity, "There is no new launches!", Toast.LENGTH_SHORT).show()}
            }
        }

        viewModel.callBackFromViewModel = callBackFromViewModel
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

    private fun subscribeToViewModel() {
        viewModel.getLaunches().observe(this,
            Observer<List<Launch>> { launches ->

                if(launches.isEmpty())
                    viewModel.getDataFromServer()
                else
                {
                    loadingIndicator.visibility = View.GONE
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
