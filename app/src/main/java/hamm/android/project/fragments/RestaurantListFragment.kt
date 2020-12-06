package hamm.android.project.fragments

import android.os.Bundle
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
import hamm.android.project.adapters.RestaurantListAdapter
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.transitionExtras
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_list.view.*


class RestaurantListFragment : Fragment(), RestaurantListAdapter.Listener {

    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        mMainActivityViewModel = ViewModelProvider(requireActivity(), RestaurantViewModelFactory(requireActivity().application)).get(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        view.floating_action_button_filter.setOnClickListener {
            findNavController().navigate(
                RestaurantListFragmentDirections.restaurantFilter(), FragmentNavigatorExtras(
                    view.floating_action_button_filter to "fab_filter"
                )
            )
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.recycler_view_restaurants?.initForRestaurants(this)
    }

    override fun onItemClick(v: View, d: Restaurant) {
        findNavController().navigate(RestaurantListFragmentDirections.restaurantDetail(d, d.info.name), d.transitionExtras(v))
    }

    override fun toggleFavorite(restaurant: Restaurant) {
        mMainActivityViewModel.updateRestaurant(restaurant)
    }

    private fun RecyclerView.initForRestaurants(listener: RestaurantListAdapter.Listener, spanCount: Int = 2) {

        val restaurantRecyclerViewAdapter = RestaurantListAdapter(listener)
        this.adapter = restaurantRecyclerViewAdapter

        val layoutManager = GridLayoutManager(context, spanCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (restaurantRecyclerViewAdapter.getItemViewType(position)) {
                    RestaurantListAdapter.VIEW_TYPE_CONTROL -> spanCount
                    RestaurantListAdapter.VIEW_TYPE_LOADING -> spanCount
                    else -> 1
                }
            }

        }
        this.layoutManager = layoutManager

        this.initPagination() { mMainActivityViewModel.getRestaurants() }
        postponeEnterTransition()
        this.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        mMainActivityViewModel.restaurants.observe(viewLifecycleOwner, { restaurants ->
            restaurantRecyclerViewAdapter.submitList(restaurants)
        })
    }

    fun RecyclerView.initPagination(paginationCallback: () -> Unit = {}) {
        this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    paginationCallback()
                }
            }
        })
    }
}