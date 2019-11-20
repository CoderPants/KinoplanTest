package kinoplan.testapp.spacexscheduler.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.activities.LaunchDetailsActivity
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.databinding.LaunchesRvElementBinding
import kinoplan.testapp.spacexscheduler.pojos.Launch

class LaunchesAdapter : RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder>() {

    var launches : List<Launch> = ArrayList()
        set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LaunchesRvElementBinding.inflate(inflater)
        return LaunchesViewHolder(binding)
    }

    override fun getItemCount(): Int = launches.size

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) = holder.bind(launches[position])

    inner class LaunchesViewHolder(private val binding: LaunchesRvElementBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(launch : Launch){
            binding.launch = launch
            binding.executePendingBindings()

            val constraintLayout = binding.activityLaunchesRvElementBaseLayout

            constraintLayout.setOnClickListener {
                val context : Context = it.context
                val intent = Intent(context, LaunchDetailsActivity::class.java)
                intent.putExtra(IntentKeys.FLIGHT_ID, launch.flight_number)
                context.startActivity(intent)
            }
        }
    }
}