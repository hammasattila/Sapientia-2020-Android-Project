package hamm.android.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hamm.android.project.viewmodels.OpenTableViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: OpenTableViewModel
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setupActionBarWithNavController(findNavController(R.id.fragment_nav_host))
        // TODO("Ez miert nem megy de a masik igen?")
        mNavController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
        setupActionBarWithNavController(
            mNavController,
            AppBarConfiguration(setOf(R.id.restaurantListFragment, R.id.profileFragment))
        )

        bottom_nav.setupWithNavController(mNavController)
        mNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.restaurantListFragment -> bottom_nav.visibility = View.VISIBLE
                R.id.profileFragment -> bottom_nav.visibility = View.VISIBLE
                else -> bottom_nav.visibility = View.GONE
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}