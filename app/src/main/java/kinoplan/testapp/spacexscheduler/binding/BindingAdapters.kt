package kinoplan.testapp.spacexscheduler.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView

import androidx.databinding.BindingAdapter

import com.squareup.picasso.Picasso

object BindingAdapters {

    @BindingAdapter("app:url", "app:errorImage")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, errorImage: Drawable) {
        Picasso.get().load(url).error(errorImage).into(view)
    }
}
