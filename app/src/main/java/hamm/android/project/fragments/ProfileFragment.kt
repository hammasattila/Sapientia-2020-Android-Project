package hamm.android.project.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.adapters.FavoritesListAdapter
import hamm.android.project.databinding.FragmentProfileBinding
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.load
import hamm.android.project.utils.transitionExtras
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.ProfileFragmentViewModel


class ProfileFragment : Fragment(), FavoritesListAdapter.Listener {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = ViewModelProvider(this).get(ProfileFragmentViewModel::class.java)
        binding.viewModel?.settings = activity?.getSharedPreferences(getString(R.string.shared_preferences_name), MODE_PRIVATE)
        binding.imageViewProfileAvatar.load(R.drawable.placeholder_avatar)

        initFavoriteList()
    }

    private fun initFavoriteList() {
        val adapter = FavoritesListAdapter(this)
        binding.recyclerViewRestaurants?.adapter = adapter
        binding.recyclerViewRestaurants?.layoutManager = LinearLayoutManager(context)
        waitForTransition(binding.recyclerViewRestaurants)
        binding.viewModel?.favoriteRestaurants?.observe(viewLifecycleOwner, { favorites ->
            adapter.submitList(favorites)
                binding.favoritesEmptyArt.visibility = when(favorites.isNullOrEmpty()) {
                    true -> View.VISIBLE
                    false -> View.GONE
            }
        })
    }

    override fun onItemClick(element: View, restaurant: Restaurant) {
        findNavController().navigate(ProfileFragmentDirections.restaurantDetailFragment(restaurant, restaurant.info.name), restaurant.transitionExtras(element))
    }

    override fun onToggleFavorite(element: View, restaurant: Restaurant) {
        binding.viewModel?.toggleFavorite(restaurant)
    }

    private fun waitForTransition(targetView: View) {
        postponeEnterTransition()
        targetView.doOnPreDraw { startPostponedEnterTransition() }
    }
}