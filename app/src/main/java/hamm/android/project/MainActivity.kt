package hamm.android.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hamm.android.project.data.OpenTableRepository
import hamm.android.project.data.OpenTableViewModel
import hamm.android.project.data.OpenTableViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mViewModel: OpenTableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val r = OpenTableRepository()
        val vm = OpenTableViewModelFactory(r)
        mViewModel = ViewModelProvider(this, vm).get(OpenTableViewModel::class.java)
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
}