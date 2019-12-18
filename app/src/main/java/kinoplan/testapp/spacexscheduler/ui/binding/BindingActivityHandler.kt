package kinoplan.testapp.spacexscheduler.ui.binding

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.ui.activities.LaunchDetailsActivity
import kinoplan.testapp.spacexscheduler.ui.activities.ZoomImageActivity
import java.lang.StringBuilder


class BindingActivityHandler {

    //Two different functions for different intent extras
    //No generics, 'cos we need different intent keys
    fun openLaunchDetailsActivity(context: Context, flightID : Int){
        val intent = Intent(context, LaunchDetailsActivity::class.java)
        intent.putExtra(IntentKeys.FLIGHT_ID, flightID)
        context.startActivity(intent)
    }

    fun openZoomImageActivity(context: Context, images : List<String>, position : Int ){
        val intent = Intent(context, ZoomImageActivity::class.java)

        //Easy for intent parsing
        val stringBuilder = StringBuilder()

        for (image in images)
            stringBuilder.append("$image ")

        intent.putExtra(IntentKeys.IMAGES, stringBuilder.toString().trim())
        intent.putExtra(IntentKeys.IMAGE_POSITION, position)
        context.startActivity(intent)
    }

    /*fun openYouTubeLink(context: Context, youtubeID : String ){
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$youtubeID"))

        try {
            context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
        }
    }

    fun openLink(context: Context, url : String ){
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intentBrowser)
    }*/
}