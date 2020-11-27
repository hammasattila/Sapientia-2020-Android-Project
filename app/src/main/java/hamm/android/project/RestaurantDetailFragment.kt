package hamm.android.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_restaurant_detail.view.*

class RestaurantDetailFragment : Fragment() {

    private val args: RestaurantDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false)

        val restaurant = args.restaurant
        view.item_restaurant_image.transitionName = "${getString(R.string.restaurant_image_transition)}_${restaurant.id}"
        view.item_restaurant_text_price.text = "${getString(R.string.restaurant_text_price)} ${restaurant.value}"
        view.item_restaurant_text_price.transitionName = "${getString(R.string.restaurant_text_price_transition)}_${restaurant.id}"
        view.item_restaurant_text_address.text = "${getString(R.string.restaurant_text_address)} ${restaurant.address}"
        view.item_restaurant_text_city.text = "${getString(R.string.restaurant_text_city)} ${restaurant.city}"
        view.item_restaurant_text_area.text = "${getString(R.string.restaurant_text_area)} ${restaurant.area}"
        view.item_restaurant_text_state.text = "${getString(R.string.restaurant_text_state)} ${restaurant.state}"
        view.item_restaurant_text_zip.text = "${getString(R.string.restaurant_text_zip)} ${restaurant.postalCode}"
        view.item_restaurant_text_phone.text = "${getString(R.string.restaurant_text_phone)} ${restaurant.phone}"
        view.item_restaurant_text_coordinates.text = "${getString(R.string.restaurant_text_coordinates)} {${restaurant.lat}, ${restaurant.lng}}"
        view.item_restaurant_text_web.text = "${getString(R.string.restaurant_text_web)} ${restaurant.urlReserve}"
        view.item_restaurant_text_web_mobile.text = "${getString(R.string.restaurant_text_web_mobile)} ${restaurant.urlMobileReserve}"

        return view
    }

}