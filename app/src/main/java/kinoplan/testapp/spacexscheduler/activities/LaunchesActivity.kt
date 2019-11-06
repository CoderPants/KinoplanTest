package kinoplan.testapp.spacexscheduler.activities

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.adapters.LaunchesAdapter
import kinoplan.testapp.spacexscheduler.models.LaunchActivityViewModel
import kinoplan.testapp.spacexscheduler.pojos.Launch

class LaunchesActivity : BaseActivity() {

    private lateinit var viewModel : LaunchActivityViewModel

    private lateinit var recyclerView : RecyclerView
    private val adapter : LaunchesAdapter = LaunchesAdapter()

    private lateinit var loadingIndicator : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        getRidOfTopBar()

        viewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)
        recyclerView = findViewById(R.id.launches_activity_rv_launches)

        loadingIndicator = findViewById(R.id.launches_activity_loading_indicator)
        loadingIndicator.visibility = View.VISIBLE

        subscribeToViewModel()
        recyclerViewCreation()

        setTopBarLogic(findViewById(R.id.launches_activity_go_back_btn),
                        findViewById(R.id.launches_activity_refresh_btn))
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
                }
            })
    }

    private fun recyclerViewCreation(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setTopBarLogic(goBackBtn : View, refreshBtn : View){
        goBackBtn.setOnClickListener { onBackPressed() }

        refreshBtn.setOnClickListener {
            loadingIndicator.visibility = View.VISIBLE
            viewModel.getDataFromServer() }
    }
}
