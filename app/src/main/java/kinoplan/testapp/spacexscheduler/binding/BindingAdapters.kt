package kinoplan.testapp.spacexscheduler.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView

import androidx.databinding.BindingAdapter

import com.squareup.picasso.Picasso
import kinoplan.testapp.spacexscheduler.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object BindingAdapters {

    @BindingAdapter("app:url", "app:errorImage")
    @JvmStatic
    fun loadImage(view: ImageView, url: String?, errorImage: Drawable) {
        if(url != null)
            Picasso.get().load(url).error(errorImage).into(view)
        else
            Picasso.get().load(R.drawable.ic_application).into(view)
    }

    @BindingAdapter("app:textTV", "app:errorText")
    @JvmStatic
    fun setText(view : TextView, text : String?, errorText: String){
        if(text == null)
            view.text = errorText
        else
            view.text = text

    }

    @BindingAdapter("app:checkImage", "app:crossImage", "app:attribute")
    @JvmStatic
    fun setImage(view : ImageView, checkImage : Drawable, crossImage : Drawable, hasAttribute : Boolean?) =
        if(hasAttribute == false || hasAttribute == null)
            view.setImageDrawable(crossImage)
        else
            view.setImageDrawable(checkImage)
}
