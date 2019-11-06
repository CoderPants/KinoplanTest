package kinoplan.testapp.spacexscheduler.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView

import androidx.databinding.BindingAdapter

import com.squareup.picasso.Picasso
import kinoplan.testapp.spacexscheduler.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BindingAdapters {

    @BindingAdapter("app:url", "app:errorImage")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, errorImage: Drawable)
        {
            Picasso.get().setIndicatorsEnabled(true)

            if(url != null)
                Picasso.get().load(url).error(errorImage).into(view)
            else
                Picasso.get().load(R.drawable.ic_application).into(view)
        }

}
