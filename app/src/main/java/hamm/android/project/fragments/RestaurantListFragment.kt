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
import hamm.android.project.databinding.FragmentRestaurantListBinding
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.transitionExtras
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory

class RestaurantListFragment : Fragment(), RestaurantListAdapter.Listener {

    private val binding by viewBinding(FragmentRestaurantListBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            binding.dataModel = ViewModelProvider(activity).get(MainActivityViewModel::class.java)
        }
        binding.floatingActionButtonFilter.setOnClickListener {
            findNavController().navigate(
                RestaurantListFragmentDirections.restaurantFilter(), FragmentNavigatorExtras(
                    binding.floatingActionButtonFilter to "fab_filter"
                )
            )
        }
        binding.recyclerViewRestaurants.initForRestaurants(this)
    }

    override fun onItemClick(v: View, d: Restaurant) {
        findNavController().navigate(RestaurantListFragmentDirections.restaurantDetail(d, d.info.name), d.transitionExtras(v))
    }

    override fun onItemToggleFavorite(restaurant: Restaurant) {
        binding.dataModel?.updateRestaurant(restaurant)
    }

    private fun RecyclerView.initForRestaurants(listener: RestaurantListAdapter.Listener, spanCount: Int = 2) {

        val restaurantRecyclerViewAdapter = RestaurantListAdapter(listener)
        this.adapter = restaurantRecyclerViewAdapter

        val layoutManager = GridLayoutManager(context, spanCount)

        this.layoutManager = layoutManager

        this.initPagination() {
            binding.lottieAnimationLoading.visibility = View.VISIBLE
            binding.dataModel?.getRestaurants()
        }
        postponeEnterTransition()
        this.viewTreeObserver.addOnPreDrawListener {
            startPostponedEnterTransition()
            true
        }

        binding.dataModel?.restaurants?.observe(viewLifecycleOwner, { restaurants ->
            restaurantRecyclerViewAdapter.submitList(restaurants)
            binding.lottieAnimationLoading.visibility = View.GONE
        })
    }

    private fun RecyclerView.initPagination(paginationCallback: () -> Unit = {}) {
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