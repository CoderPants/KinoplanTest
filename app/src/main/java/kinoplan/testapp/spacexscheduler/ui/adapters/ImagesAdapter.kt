package kinoplan.testapp.spacexscheduler.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kinoplan.testapp.spacexscheduler.databinding.ImagesRvElementBinding
import kinoplan.testapp.spacexscheduler.ui.binding.BindingHandler

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    var images : List<String> = ArrayList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    val handler : BindingHandler = BindingHandler()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImagesRvElementBinding.inflate(inflater)
        return ImagesViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) =
        holder.bind(image = images[position], position = position)

    inner class ImagesViewHolder(private val binding: ImagesRvElementBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image : String, position : Int){
            binding.url = image
            binding.images = images
            binding.position = position
            binding.handler = handler
            binding.executePendingBindings()
        }
    }
}