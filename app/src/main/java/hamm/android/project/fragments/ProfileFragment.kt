package hamm.android.project.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import hamm.android.project.R
import hamm.android.project.adapters.FavoriteListAdapter
import hamm.android.project.databinding.FragmentProfileBinding
import hamm.android.project.utils.load
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.ProfileFragmentViewModel


class ProfileFragment : Fragment() {

    private val binding by viewBinding(FragmentProfileBinding::bind)

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
        val adapter = FavoriteListAdapter()
        binding.recyclerViewRestaurants?.adapter = adapter
        binding.recyclerViewRestaurants?.layoutManager = LinearLayoutManager(context)
        binding.viewModel?.favoriteRestaurants?.observe(viewLifecycleOwner, { favorites ->
            adapter.submitList(favorites)
        })
    }


}