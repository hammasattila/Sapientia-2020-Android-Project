package hamm.android.project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.utils.Constants
import kotlinx.android.synthetic.main.fragment_restaurant_filter.view.*

class RestaurantFilterFragment : Fragment() {

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
        view.auto_complete_text_view_states.setAdapter(statesArrayAdapter)

        return view

    }

}