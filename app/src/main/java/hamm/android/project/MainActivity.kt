package hamm.android.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import hamm.android.project.viewmodels.OpenTableViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: OpenTableViewModel
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setupActionBarWithNavController(findNavController(R.id.fragment_nav_host))
        // TODO("Ez miert nem megy de a masik igen?")
        mNavController = (supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment).navController
        setupActionBarWithNavController(mNavController)


//        val r = OpenTableRepository()
//        val vm = OpenTableViewModelFactory(r)
//        mViewModel = ViewModelProvider(this, vm).get(OpenTableViewModel::class.java)
//        mViewModel.getCities()
//        mViewModel.getRestaurants("WY")
//        val ad = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>())
//        mViewModel.cities.observe(this, { cities ->
//            ad.clear()
//            ad.addAll(cities)
//            Log.d("Cities", cities.size.toString() )
//            Log.d("Cities", cities.joinToString(", "))
//        })
//
//        mViewModel.restaurants.observe(this, { restaurants ->
//            Log.d("Restaurants", restaurants.size.toString())
//        })
//
//        multi_auto_complete_text_view_cities.setAdapter(ad)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp() || super.onSupportNavigateUp()
    }
}