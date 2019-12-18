package kinoplan.testapp.spacexscheduler.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.ui.binding.BindingActivityHandler
import kinoplan.testapp.spacexscheduler.databinding.LaunchesRvElementBinding
import kinoplan.testapp.spacexscheduler.pojos.Launch
import kotlin.collections.ArrayList

class LaunchesAdapter : RecyclerView.Adapter<LaunchesAdapter.LaunchesViewHolder>() {

    private var launches : MutableList<Launch> = ArrayList()

    fun setNewData(new : List<Launch>){
        launches.clear()
        launches.addAll(new)
        notifyDataSetChanged()
    }

    val activityHandler : BindingActivityHandler = BindingActivityHandler()

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
            binding.handler = activityHandler
            binding.executePendingBindings()
        }
    }
}