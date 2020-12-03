package hamm.android.project.fragments

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.adapters.RestaurantRecyclerViewAdapter
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.initPagination
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*
import kotlinx.android.synthetic.main.recycle_view_item_restaurant.view.*


class RestaurantListFragment : Fragment(), RestaurantRecyclerViewAdapter.Listener {

    private lateinit var mViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        mViewModel = ViewModelProvider(requireActivity(), RestaurantViewModelFactory(requireActivity().application)).get(RestaurantViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        view.recycler_view_restaurants.initForRestaurants(this)
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

    private fun RecyclerView.initForRestaurants(listener: RestaurantRecyclerViewAdapter.Listener, spanCount: Int = 2) {

        val restaurantRecyclerViewAdapter = RestaurantRecyclerViewAdapter(listener)
        this.adapter = restaurantRecyclerViewAdapter

        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (restaurantRecyclerViewAdapter.getItemViewType(position)) {
                    RestaurantRecyclerViewAdapter.VIEW_TYPE_CONTROL -> spanCount
                    RestaurantRecyclerViewAdapter.VIEW_TYPE_LOADING -> spanCount
                    else -> 1
                }
            }

        }
        this.layoutManager = layoutManager

        this.initPagination() { mViewModel.getRestaurants() }
        postponeEnterTransition()
        this.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        mViewModel.restaurants.observe(viewLifecycleOwner, { restaurants ->
            restaurantRecyclerViewAdapter.setData(restaurants)
        })
    }
}