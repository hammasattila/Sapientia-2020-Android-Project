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
import com.google.android.material.chip.Chip
import hamm.android.project.R
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.databinding.FragmentRestaurantFilterBinding
import hamm.android.project.utils.delayedCheckForLoading
import hamm.android.project.utils.viewBinding
import hamm.android.project.viewmodels.MainActivityViewModel
import hamm.android.project.viewmodels.RestaurantFilterFragmentViewModel

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
        return inflater.inflate(R.layout.fragment_restaurant_filter, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = ViewModelProvider(this).get(RestaurantFilterFragmentViewModel::class.java)
        binding.dataModel = activity?.let { ViewModelProvider(it).get(MainActivityViewModel::class.java) }

        // Init filters.
        context?.let { context ->
            binding.viewModel?.let { viewModel ->
                binding.spinnerCountry.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, viewModel.countries)
//                binding.spinnerPrice.adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.price_range))

            }
            binding.autoCompleteTextViewState.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, RestaurantRepository.states))
            binding.autoCompleteTextViewCity.setAdapter(ArrayAdapter(context, android.R.layout.simple_list_item_1, RestaurantRepository.cities))
        }

        binding.spinnerCountry.setSelection(RestaurantRepository.countries.indexOf(binding.dataModel?.country))
        binding.autoCompleteTextViewState.setText(RestaurantRepository.mapOfStates[binding.dataModel?.state])
        binding.spinnerPrice.setSelection(binding.dataModel?.price ?: 0)


        binding.dataModel?.cities?.forEach { addCityChip(it) }
        binding.buttonAddCityFilter.setOnClickListener { addCityFilter() }
        binding.floatingActionButtonFilter.setOnClickListener { applyFilters() }
    }

    private fun addCityFilter() {
        binding.dataModel?.let {dataModel->
            if (binding.autoCompleteTextViewCity.text.isNullOrBlank()) {
                binding.autoCompleteTextViewCity.setText("")
                return
            }

            dataModel.city = binding.autoCompleteTextViewCity.text.toString().trim()
            binding.autoCompleteTextViewCity.setText(dataModel.city!!)

            if (!dataModel.cities.contains(dataModel.city)) {
                dataModel.cities.add(dataModel.city!!)
                addCityChip(dataModel.city!!)
            }

            binding.autoCompleteTextViewCity.setText("")
        }
    }

    private fun addCityChip(city: String) {
        val inflater = LayoutInflater.from(context)
        val chip = inflater.inflate(R.layout.item_chip, null, false) as Chip
        chip.text = city
        chip.setOnCloseIconClickListener { view -> binding.chipGroupCities.removeView(view); binding.dataModel?.cities?.removeIf { it == city } }
        binding.chipGroupCities.addView(chip)
    }

    private fun applyFilters() {
        val country = RestaurantRepository.curateCountry(binding.spinnerCountry.selectedItem.toString())
        binding.spinnerCountry.setSelection(RestaurantRepository.countries.indexOf(country))
        binding.dataModel?.price = if (binding.spinnerPrice.selectedItemPosition == 0) null else binding.spinnerPrice.selectedItemPosition

        val state = RestaurantRepository.curateState(binding.autoCompleteTextViewState.text.toString())
        binding.autoCompleteTextViewState.setText(state)
        val stateCode = RestaurantRepository.getStateCode(state)

        binding.floatingActionButtonFilter.animate()
            .alpha(0.0F)
            .scaleX(0.0F)
            .scaleY(0.0F)
            .setDuration(100)
            .withStartAction { binding.lottieLoading.visibility = View.VISIBLE }
            .withEndAction { binding.floatingActionButtonFilter.visibility = View.INVISIBLE }

        when (binding.dataModel?.setFilters(country, stateCode)) {
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