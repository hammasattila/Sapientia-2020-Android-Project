package hamm.android.project.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import hamm.android.project.R
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var mViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity(), RestaurantViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading()
    }

    private fun loading() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (!mViewModel.loading) {
                findNavController().navigate(
                    SplashFragmentDirections.restaurantListFragment(),
                    NavOptions.Builder()
                        .setPopUpTo(R.id.splashFragment, true)
                        .setEnterAnim(R.anim.anim_enter_from_right)
                        .setExitAnim(R.anim.anim_exit_to_left)
                        .build()
                )
            } else {
                this.loading()
            }
        }, 5000)
    }

}