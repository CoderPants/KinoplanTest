package kinoplan.testapp.spacexscheduler.binding

import android.content.Context
import android.content.Intent
import android.view.View
import kinoplan.testapp.spacexscheduler.activities.LaunchDetailsActivity
import kinoplan.testapp.spacexscheduler.constants.IntentKeys

class BindingHandler {

    fun openLaunchDetailsActivity(view : View, flightID : Int){
        val context : Context = view.context
        val intent = Intent(context, LaunchDetailsActivity::class.java)
        intent.putExtra(IntentKeys.FLIGHT_ID, flightID)
        context.startActivity(intent)
    }
}