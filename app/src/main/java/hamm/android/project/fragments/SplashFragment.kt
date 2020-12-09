package hamm.android.project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import hamm.android.project.R
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.utils.delayedCheckForLoading

class SplashFragment : Fragment() {

    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            mMainActivityViewModel = ViewModelProvider(it).get(MainActivityViewModel::class.java)
            loading()
        }
    }

    private fun loading() {
        delayedCheckForLoading(mMainActivityViewModel, 5000L) {
            findNavController().navigate(
                SplashFragmentDirections.restaurantListFragment(),
                NavOptions.Builder()
                    .setPopUpTo(R.id.splashFragment, true)
                    .setEnterAnim(R.anim.anim_enter_from_right)
                    .setExitAnim(R.anim.anim_exit_to_left)
                    .build()
            )
        }
    }

}