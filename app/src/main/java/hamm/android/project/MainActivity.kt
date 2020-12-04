package hamm.android.project

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hamm.android.project.viewmodels.RestaurantViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    // private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var mNavController: NavController
    private lateinit var mViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // setContentView(binding.root)

        mViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        mNavController = (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
        setupActionBarWithNavController(mNavController, AppBarConfiguration(setOf(R.id.restaurantListFragment, R.id.profileFragment)))
        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> supportActionBar?.hide()
                else -> supportActionBar?.show()
            }
        }

        bottom_nav.setupWithNavController(mNavController)
        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> bottom_nav.animate()
                    .translationY(bottom_nav.height.toFloat())
                    .setDuration(300)
                    .withStartAction { bottom_nav.visibility = View.INVISIBLE }
                R.id.restaurantListFragment -> bottom_nav.animate()
                    .translationY(0.0F)
                    .setDuration(300)
                    .withStartAction { bottom_nav.visibility = View.VISIBLE }
                R.id.profileFragment -> bottom_nav.animate()
                    .translationY(0.0F)
                    .setDuration(300)
                    .withStartAction { bottom_nav.visibility = View.VISIBLE }
                else -> bottom_nav.animate()
                    .translationY(bottom_nav.height.toFloat())
                    .setDuration(300)
                    .withEndAction { bottom_nav.visibility = View.GONE }
            }
        }
    }

    override fun onBackPressed() {
        if (!mViewModel.loading) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!mViewModel.loading) {
            return mNavController.navigateUp() || super.onSupportNavigateUp()
        }

        return false
    }
}