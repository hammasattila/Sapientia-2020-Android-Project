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
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_filter.view.*

class RestaurantFilterFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_restaurant_filter, container, false)

        // Get ViewModel.
        mViewModel =
            ViewModelProvider(
                requireActivity(),
                RestaurantViewModelFactory(requireActivity().application)
            )
                .get(RestaurantViewModel::class.java)

        // Init filters.
        //Country
        view.spinner_country.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            RestaurantViewModel.countries
        )
        view.spinner_country.setSelection(
            RestaurantViewModel.countries.indexOf(
                mViewModel.country
            )
        )

        // State
        view.auto_complete_text_view_state.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                RestaurantViewModel.states
            )
        )
        view.auto_complete_text_view_state.setText(RestaurantViewModel.mapOfStates[mViewModel.state])

        // City
        view.auto_complete_text_view_city.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                mViewModel.cities
            )
        )
        view.auto_complete_text_view_city.setText(mViewModel.city)

        // Zip
        view.edit_text_view_zip.setText(mViewModel.zip)

        // Address
        view.edit_text_view_address.setText(mViewModel.address)

        // Name
        view.edit_text_view_name.setText(mViewModel.name)

        // Per page
        view.spinner_per_page.adapter = ArrayAdapter<Int>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            RestaurantViewModel.numberOfRestaurantsPerPage
        )
        view.spinner_per_page.setSelection(
            RestaurantViewModel.numberOfRestaurantsPerPage.indexOf(
                mViewModel.perPage
            ), true
        )


        // Click listeners
        view.floating_action_button_filter.setOnClickListener { v ->
            val country = mViewModel.curateCountry(view.spinner_country.selectedItem.toString())
            view.spinner_country.setSelection(
                RestaurantViewModel.countries.indexOf(
                    mViewModel.country
                )
            )

            val state = mViewModel.curateState(view.auto_complete_text_view_state.text.toString())
            view.auto_complete_text_view_state.setText(state)
            val stateCode = RestaurantViewModel.getStateCode(state)

            // val city = mViewModel.curateCity(view.auto_complete_text_view_city.text.toString())
            val city = view.auto_complete_text_view_city.text.toString()

            val zip = view.edit_text_view_zip.text.toString()

            val address = view.edit_text_view_address.text.toString()

            val name = view.edit_text_view_name.text.toString()

            val perPage: Int = view.spinner_per_page.selectedItem as Int

            mViewModel.setFilters(country, stateCode, city, zip, address, name, perPage)
        }

        return view

    }

}