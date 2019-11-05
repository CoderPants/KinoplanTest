package kinoplan.testapp.spacexscheduler.activities

import android.util.Log
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launches)
        getRidOfTopBar()

        viewModel = ViewModelProviders.of(this).get(LaunchActivityViewModel::class.java)
        recyclerView = findViewById(R.id.launches_activity_rv_launches)

        subscribeToViewModel()
        recyclerViewCreation()
    }

    private fun subscribeToViewModel() {
        viewModel.getLaunches().observe(this,
            Observer<List<Launch>> { launches ->

                if(launches.isEmpty())
                    viewModel.getDataFromServer()
                else
                    adapter.launches = launches

            })
    }

    private fun recyclerViewCreation(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}
