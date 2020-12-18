package hamm.android.project

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hamm.android.project.databinding.ActivityMainBinding
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.MainActivityViewModel


class MainActivity : AppCompatActivity() {


    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var mNavController: NavController
    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        mMainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mNavController = (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
        setupActionBarWithNavController(mNavController, AppBarConfiguration(setOf(R.id.restaurantListFragment, R.id.profileFragment, R.id.appInfoFragment)))
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> supportActionBar?.hide()
                else -> supportActionBar?.show()
            }
        }

        binding.bottomNav.setupWithNavController(mNavController)
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> binding.bottomNav.animate()
                    .translationY(binding.bottomNav.height.toFloat())
                    .setDuration(500)
                    .withStartAction { binding.bottomNav.visibility = View.GONE }
                R.id.restaurantListFragment, R.id.profileFragment, R.id.appInfoFragment -> binding.bottomNav.animate()
                    .translationY(0.0F)
                    .setDuration(500)
                    .withStartAction {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                else -> binding.bottomNav.animate()
                    .translationY(binding.bottomNav.height.toFloat())
                    .setDuration(500)
                    .withEndAction { binding.bottomNav.visibility = View.GONE }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {
        if (!mMainActivityViewModel.loading) {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!mMainActivityViewModel.loading) {
            return mNavController.navigateUp() || super.onSupportNavigateUp()
        }

        return false
    }
}