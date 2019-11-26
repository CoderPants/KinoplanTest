package kinoplan.testapp.spacexscheduler.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kinoplan.testapp.spacexscheduler.R
import kinoplan.testapp.spacexscheduler.constants.ConstantsForApp
import kinoplan.testapp.spacexscheduler.constants.IntentKeys
import kinoplan.testapp.spacexscheduler.databinding.ZoomFragmentBinding

class ZoomImageFragment : Fragment() {

    lateinit var binding : ZoomFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return try {
            binding = DataBindingUtil.inflate(inflater, R.layout.zoom_fragment, container, false)
            val view = binding.root
            binding.url = arguments?.getString(IntentKeys.IMAGE_URL)
            binding.root
        } catch (e: Exception) {
            Log.wtf(
                ConstantsForApp.LOG_TAG,
                "Exception in AlarmClockFragment in onCreateView method $e"
            )
            super.onCreateView(inflater, container, savedInstanceState)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            super.onViewCreated(view, savedInstanceState)
        }   catch (e : java.lang.Exception)
        {
            Log.i(ConstantsForApp.LOG_TAG, "Here ", e)
        }


    }
}