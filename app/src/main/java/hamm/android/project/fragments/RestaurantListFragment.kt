package hamm.android.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.adapters.RestaurantRecyclerViewAdapter
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import hamm.android.project.model.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*
import kotlinx.android.synthetic.main.recycle_view_item_restaurant.view.*

class RestaurantListFragment : Fragment(), RestaurantRecyclerViewAdapter.Listener {

    private lateinit var mViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)


        val restaurantRecyclerViewAdapter = RestaurantRecyclerViewAdapter(this)
        view.recycler_view_restaurants.layoutManager = LinearLayoutManager(context)
        // view.recycler_view_restaurants.layoutManager = GridLayoutManager(context, 3)
        view.recycler_view_restaurants.adapter = restaurantRecyclerViewAdapter
        postponeEnterTransition()
        view.recycler_view_restaurants.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        mViewModel = ViewModelProvider(requireActivity(), RestaurantViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
        mViewModel.restaurants.observe(viewLifecycleOwner, { restaurants ->
            restaurantRecyclerViewAdapter.setData(restaurants)
        })

        view.floating_action_button_filter.setOnClickListener {
            findNavController().navigate(
                RestaurantListFragmentDirections.restaurantFilter(), FragmentNavigatorExtras(
                    view.floating_action_button_filter to "fab_filter"
                )
            )
        }

        return view
    }

    override fun onItemClick(v: View, d: Restaurant) {
        findNavController().navigate(
            RestaurantListFragmentDirections.restaurantDetail(d, d.name), FragmentNavigatorExtras(
                v.item_restaurant_image to "${getString(R.string.restaurant_image_transition)}_${d.id}",
                v.item_restaurant_text_price to "${getString(R.string.restaurant_text_price_transition)}_${d.id}"
            )
        )
    }

    override fun onItemLongClick() {
        TODO("Not yet implemented")
    }
}