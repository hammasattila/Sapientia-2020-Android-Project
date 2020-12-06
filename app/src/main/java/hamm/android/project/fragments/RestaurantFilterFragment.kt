package hamm.android.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.utils.delayedCheckForLoading
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_filter.view.*

class RestaurantFilterFragment : Fragment() {

    private lateinit var mMainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_filter, container, false)

        // Get ViewModel.
        mMainActivityViewModel =
            ViewModelProvider(
                requireActivity(),
                RestaurantViewModelFactory(requireActivity().application)
            )
                .get(MainActivityViewModel::class.java)

        // Init filters.
        //Country
        view.spinner_country.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            RestaurantRepository.countries
        )
        view.spinner_country.setSelection(
            RestaurantRepository.countries.indexOf(
                mMainActivityViewModel.country
            )
        )

        // State
        view.auto_complete_text_view_state.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                RestaurantRepository.states
            )
        )
        view.auto_complete_text_view_state.setText(RestaurantRepository.mapOfStates[mMainActivityViewModel.state])

        // City
        view.auto_complete_text_view_city.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                RestaurantRepository.cities
            )
        )
        view.auto_complete_text_view_city.setText(mMainActivityViewModel.city)

        // Zip
        view.edit_text_view_zip.setText(mMainActivityViewModel.zip)

        // Address
        view.edit_text_view_address.setText(mMainActivityViewModel.address)

        // Name
        view.edit_text_view_name.setText(mMainActivityViewModel.name)

        // Per page
        view.spinner_per_page.adapter = ArrayAdapter<Int>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            RestaurantRepository.numberOfRestaurantsPerPage
        )
        view.spinner_per_page.setSelection(
            RestaurantRepository.numberOfRestaurantsPerPage.indexOf(
                mMainActivityViewModel.perPage
            ), true
        )


        // Click listeners
        view.floating_action_button_filter.setOnClickListener { v ->
            val country = mMainActivityViewModel.curateCountry(view.spinner_country.selectedItem.toString())
            view.spinner_country.setSelection(
                RestaurantRepository.countries.indexOf(
                    mMainActivityViewModel.country
                )
            )

            val state = mMainActivityViewModel.curateState(view.auto_complete_text_view_state.text.toString())
            view.auto_complete_text_view_state.setText(state)
            val stateCode = RestaurantRepository.getStateCode(state)

            // val city = mMainActivityViewModel.curateCity(view.auto_complete_text_view_city.text.toString())
            val city = view.auto_complete_text_view_city.text.toString()

            val zip = view.edit_text_view_zip.text.toString()

            val address = view.edit_text_view_address.text.toString()

            val name = view.edit_text_view_name.text.toString()

            val perPage: Int = view.spinner_per_page.selectedItem as Int

            view.floating_action_button_filter.animate()
                .alpha(0.0F)
                .scaleX(0.0F)
                .scaleY(0.0F)
                .setDuration(100)
                .withStartAction { view.lottie_loading.visibility = View.VISIBLE }
                .withEndAction { view.floating_action_button_filter.visibility = View.INVISIBLE }

            mMainActivityViewModel.setFilters(country, stateCode, city, zip, address, name, perPage)
            delayedCheckForLoading(mMainActivityViewModel) {
                view.floating_action_button_filter.animate()
                    .alpha(1.0F)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .setDuration(100)
                    .withStartAction { view.floating_action_button_filter.visibility = View.VISIBLE }
                    .withEndAction { findNavController().popBackStack() }
            }
        }

        return view

    }

}