package hamm.android.project.fragments

import android.os.Bundle
import android.util.Log
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
import hamm.android.project.data.OpenTableRepository
import hamm.android.project.utils.Constants
import hamm.android.project.viewmodels.OpenTableViewModel
import hamm.android.project.viewmodels.OpenTableViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_filter.view.*

class RestaurantFilterFragment : Fragment() {

    private lateinit var mViewModel: OpenTableViewModel

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

        val states = Constants.states.map { it.value }
        val statesArrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, states)

        mViewModel = ViewModelProvider(requireActivity(), OpenTableViewModelFactory(OpenTableRepository()))
            .get(OpenTableViewModel::class.java)

        view.auto_complete_text_view_states.setAdapter(statesArrayAdapter)
        view.auto_complete_text_view_states.setText(Constants.states[mViewModel.state])

        view.floating_action_button_filter.setOnClickListener { v ->
            val state = checkInputState(view.auto_complete_text_view_states.text.toString())
            if (null != state) {
                mViewModel.state = state
            }
            findNavController().popBackStack()
        }

        return view

    }

    private fun checkInputState(state: String): String? {
        var res: String? = null
        try {
            if (state.isNotBlank()) {
                res = Constants.states.filterValues { it == state }.keys.elementAt(0)
            }
        } catch (e: IndexOutOfBoundsException) {
            Toast.makeText(context, "No such state.", Toast.LENGTH_SHORT).show()
        } finally {
            return res
        }
    }

}