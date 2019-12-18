package kinoplan.testapp.spacexscheduler.ui.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import kinoplan.testapp.spacexscheduler.R

object BindingImageAdapters {

    @BindingAdapter("app:url", "app:errorImage")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, errorImage: Drawable) {
        if(url != null)
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .error(errorImage)
                .into(view)
        else
            Picasso.get()
                .load(R.drawable.ic_application)
                .into(view)
    }

    @BindingAdapter("app:bigImage", "app:errorImage")
    @JvmStatic
    fun loadBigImage(view: ImageView, url: String?, errorImage: Drawable) {
        if(url != null)
            Picasso.get()
                .load(url)
                .error(errorImage)
                .into(view)
        else
            Picasso.get()
                .load(R.drawable.ic_application)
                .into(view)
    }

    @BindingAdapter("app:checkImage", "app:crossImage", "app:attribute")
    @JvmStatic
    fun setImage(view : ImageView, checkImage : Drawable, crossImage : Drawable, hasAttribute : Boolean?) =
        if(hasAttribute == false || hasAttribute == null)
            view.setImageDrawable(crossImage)
        else
            view.setImageDrawable(checkImage)
}