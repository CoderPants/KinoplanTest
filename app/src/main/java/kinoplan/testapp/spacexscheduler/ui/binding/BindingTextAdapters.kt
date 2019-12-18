package kinoplan.testapp.spacexscheduler.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingTextAdapters {

    @BindingAdapter("app:textTV", "app:errorText")
    @JvmStatic
    fun setText(view : TextView, text : String?, errorText: String){
        if(text == null)
            view.text = errorText
        else
            view.text = text

    }
}