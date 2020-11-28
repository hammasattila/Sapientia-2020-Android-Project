package hamm.android.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.utils.Constants
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.OpenTableViewModelFactory
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
            ViewModelProvider(requireActivity(), OpenTableViewModelFactory(RestaurantRepository()))
                .get(RestaurantViewModel::class.java)

        // Init filters.
        view.auto_complete_text_view_states.setAdapter(
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                RestaurantViewModel.states
            )
        )
        view.auto_complete_text_view_states.setText(RestaurantViewModel.mapOfStates[mViewModel.state])

        view.floating_action_button_filter.setOnClickListener { v ->
            val state = mViewModel.curateState(view.auto_complete_text_view_states.text.toString())
            view.auto_complete_text_view_states.setText(state)
            val stateCode = RestaurantViewModel.getStateCode(state)
            stateCode?.let {
                mViewModel.state = stateCode
            }
            findNavController().popBackStack()
        }

        return view

    }

}