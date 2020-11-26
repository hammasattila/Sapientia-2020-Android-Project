package hamm.android.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import hamm.android.project.adapters.RestaurantRecyclerViewAdapter
import hamm.android.project.data.OpenTableRepository
import hamm.android.project.data.OpenTableViewModel
import hamm.android.project.data.OpenTableViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_list.*
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*

class RestaurantListFragment : Fragment(), RestaurantRecyclerViewAdapter.Listener {

    private lateinit var mViewModel: OpenTableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)


        val restaurantRecyclerViewAdapter = RestaurantRecyclerViewAdapter(this)
        view.recycler_view_restaurants.layoutManager = LinearLayoutManager(context)
        view.recycler_view_restaurants.adapter = restaurantRecyclerViewAdapter

        val r = OpenTableRepository()
        val vm = OpenTableViewModelFactory(r)
        mViewModel = ViewModelProvider(this, vm).get(OpenTableViewModel::class.java)
//        mViewModel.getCities()
        mViewModel.getRestaurants("WY")
//        val ad = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList<String>())
//        mViewModel.cities.observe(this, { cities ->
//            ad.clear()
//            ad.addAll(cities)
//            Log.d("Cities", cities.size.toString() )
//            Log.d("Cities", cities.joinToString(", "))
//        })
//
        mViewModel.restaurants.observe(viewLifecycleOwner, { restaurants ->
            restaurantRecyclerViewAdapter.setData(restaurants)
        })
//
//        multi_auto_complete_text_view_cities.setAdapter(ad)

//        // Animation
//        sharedElementEnterTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        postponeEnterTransition()
//        mRecyclerView.viewTreeObserver.addOnPreDrawListener {
//            startPostponedEnterTransition()
//            true
//        }
//
//        // ViewModel
//        activity?.let { activity ->
//            mFoodViewModel =
//                ViewModelProvider(this, FoodViewModelFactory(activity.application)).get(
//                    FoodViewModel::class.java
//                )
//        }
//        mFoodViewModel.allFoods.observe(viewLifecycleOwner, Observer { food ->
//            mRecyclerViewAdapter.setData(food)
//        })

        return view
    }

    override fun onItemClick() {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick() {
        TODO("Not yet implemented")
    }
}