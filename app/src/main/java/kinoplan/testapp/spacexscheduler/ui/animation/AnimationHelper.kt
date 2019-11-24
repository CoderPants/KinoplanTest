package kinoplan.testapp.spacexscheduler.ui.animation

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.constraintlayout.widget.ConstraintLayout


object AnimationHelper {

    fun expand(view: View) {
        val matchParentMeasureSpec: Int = View.MeasureSpec.makeMeasureSpec(
            (view.parent as View).width,
            View.MeasureSpec.EXACTLY
        )

        val wrapContentMeasureSpec: Int =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(matchParentMeasureSpec, wrapContentMeasureSpec)

        val targetHeight: Int = view.measuredHeight
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        view.layoutParams.height = 1
        view.visibility = View.VISIBLE

        val animation: Animation = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) ConstraintLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()

                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Expansion speed of 4dp/ms
        animation.duration = ((targetHeight / view.context.resources.displayMetrics.density)*4).toLong()
        view.startAnimation(animation)
    }

    fun collapse(view: View) {
        val initialHeight: Int = view.measuredHeight

        val animation: Animation = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f)
                    view.visibility = View.GONE
                 else {
                    view.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        // Collapse speed of 4dp/ms
        animation.duration = ((initialHeight / view.context.resources.displayMetrics.density)*4).toLong()
        view.startAnimation(animation)
    }
}