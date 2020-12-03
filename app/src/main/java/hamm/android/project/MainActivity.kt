package hamm.android.project

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hamm.android.project.databinding.ActivityMainBinding
import hamm.android.project.utils.viewBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        // setupActionBarWithNavController(findNavController(R.id.fragment_nav_host))
        // TODO("Ez miert nem megy de a masik igen?")
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
        setupActionBarWithNavController(
            mNavController,
            AppBarConfiguration(setOf(R.id.restaurantListFragment, R.id.profileFragment))
        )
        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> supportActionBar?.hide()
                else -> supportActionBar?.show()
            }
        }

        bottom_nav.setupWithNavController(mNavController)
        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> bottom_nav.animate().translationY(bottom_nav.height.toFloat()).setDuration(300).withStartAction { bottom_nav.visibility = View.INVISIBLE }
                R.id.restaurantListFragment -> bottom_nav.animate().translationY(0.0F).setDuration(300).withStartAction { bottom_nav.visibility = View.VISIBLE }
                R.id.profileFragment -> bottom_nav.animate().translationY(0.0F).setDuration(300).withStartAction { bottom_nav.visibility = View.VISIBLE }
                else -> bottom_nav.animate().translationY(bottom_nav.height.toFloat()).setDuration(300).withEndAction { bottom_nav.visibility = View.GONE }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}