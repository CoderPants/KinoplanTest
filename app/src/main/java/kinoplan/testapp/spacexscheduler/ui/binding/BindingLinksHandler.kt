package kinoplan.testapp.spacexscheduler.ui.binding

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class BindingLinksHandler {

    fun openYouTubeLink(context: Context, youtubeID : String ){
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
    }
}