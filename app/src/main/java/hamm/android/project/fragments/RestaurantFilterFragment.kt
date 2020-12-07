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
import hamm.android.project.databinding.FragmentRestaurantFilterBinding
import hamm.android.project.utils.delayedCheckForLoading
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.viewmodels.RestaurantFilterFragmentViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory

class RestaurantFilterFragment : Fragment() {

    private val binding by viewBinding(FragmentRestaurantFilterBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_restaurant_filter, container, false)

//        // Click listeners
//        view.floating_action_button_filter.setOnClickListener { v ->
//            val country = mMainActivityViewModel.curateCountry(view.spinner_country.selectedItem.toString())
//            view.spinner_country.setSelection(
//                RestaurantRepository.countries.indexOf(
//                    mMainActivityViewModel.country
//                )
//            )
//
//            val state = mMainActivityViewModel.curateState(view.auto_complete_text_view_state.text.toString())
//            view.auto_complete_text_view_state.setText(state)
//            val stateCode = RestaurantRepository.getStateCode(state)
//
//            // val city = mMainActivityViewModel.curateCity(view.auto_complete_text_view_city.text.toString())
//            val city = view.auto_complete_text_view_city.text.toString()
//
//            val zip = view.edit_text_view_zip.text.toString()
//
//            val address = view.edit_text_view_address.text.toString()
//
//            val name = view.edit_text_view_name.text.toString()
//
//            val perPage: Int = view.spinner_per_page.selectedItem as Int
//
//            view.floating_action_button_filter.animate()
//                .alpha(0.0F)
//                .scaleX(0.0F)
//                .scaleY(0.0F)
//                .setDuration(100)
//                .withStartAction { view.lottie_loading.visibility = View.VISIBLE }
//                .withEndAction { view.floating_action_button_filter.visibility = View.INVISIBLE }
//
//            mMainActivityViewModel.setFilters(country, stateCode, city, zip, address, name, perPage)
//            delayedCheckForLoading(mMainActivityViewModel) {
//                view.floating_action_button_filter.animate()
//                    .alpha(1.0F)
//                    .scaleX(1.0F)
//                    .scaleY(1.0F)
//                    .setDuration(100)
//                    .withStartAction { view.floating_action_button_filter.visibility = View.VISIBLE }
//                    .withEndAction { findNavController().popBackStack() }
//            }
//        }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = ViewModelProvider(this).get(RestaurantFilterFragmentViewModel::class.java)
        binding.dataModel = activity?.let { ViewModelProvider(it).get(MainActivityViewModel::class.java) }

        // Init filters.
        context?.let { context ->
            binding.viewModel?.let { viewModel ->
                binding.spinnerCountry.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, viewModel.countries)
                binding.spinnerPerPage.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, viewModel.perPage)
            }
            binding.autoCompleteTextViewState.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, RestaurantRepository.states))
            binding.autoCompleteTextViewCity.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, RestaurantRepository.cities))
        }

        binding.spinnerCountry.setSelection(RestaurantRepository.countries.indexOf(binding.dataModel?.country))
        binding.autoCompleteTextViewState.setText(RestaurantRepository.mapOfStates[binding.dataModel?.state])
        binding.spinnerPerPage.setSelection(RestaurantRepository.numberOfRestaurantsPerPage.indexOf(binding.dataModel?.perPage), true)

        binding.floatingActionButtonFilter.setOnClickListener { applayFilters() }
    }

    fun applayFilters() {
        val country = binding.dataModel?.curateCountry(binding.spinnerCountry.selectedItem.toString())
        binding.spinnerCountry.setSelection(RestaurantRepository.countries.indexOf(binding.dataModel?.country))

        val state = binding.dataModel?.curateState(binding.autoCompleteTextViewState.text.toString())
        binding.autoCompleteTextViewState.setText(state)
        val stateCode = RestaurantRepository.getStateCode(state)
        val perPage: Int = binding.spinnerPerPage.selectedItem as Int

        binding.floatingActionButtonFilter.animate()
            .alpha(0.0F)
            .scaleX(0.0F)
            .scaleY(0.0F)
            .setDuration(100)
            .withStartAction { binding.lottieLoading.visibility = View.VISIBLE }
            .withEndAction { binding.floatingActionButtonFilter.visibility = View.INVISIBLE }

        when (binding.dataModel?.setFilters(country, stateCode, perPage)) {
            true -> delayedCheckForLoading(binding.dataModel) {
                binding.floatingActionButtonFilter.animate()
                    .alpha(1.0F)
                    .scaleX(1.0F)
                    .scaleY(1.0F)
                    .setDuration(100)
                    .withStartAction { binding.floatingActionButtonFilter.visibility = View.VISIBLE }
                    .withEndAction { findNavController().popBackStack() }
            }
        }
    }

}