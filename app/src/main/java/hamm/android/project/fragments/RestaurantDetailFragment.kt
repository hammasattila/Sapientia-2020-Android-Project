package hamm.android.project.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import hamm.android.project.R
import hamm.android.project.viewmodels.RestaurantDetailViewModel
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.fragment_restaurant_detail.view.*
import java.util.*


class RestaurantDetailFragment : Fragment() {

    private val args: RestaurantDetailFragmentArgs by navArgs()
    private lateinit var mRestaurantViewModel: RestaurantViewModel
    private lateinit var mRestaurantDetailViewModel: RestaurantDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        mRestaurantViewModel = ViewModelProvider(
            requireActivity(),
            RestaurantViewModelFactory(requireActivity().application)
        ).get(RestaurantViewModel::class.java)
        mRestaurantDetailViewModel =
            ViewModelProvider(this).get(RestaurantDetailViewModel::class.java)
        mRestaurantDetailViewModel.restaurant = args.restaurant
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.let {
            val restaurant = mRestaurantDetailViewModel.restaurant
            it.item_restaurant_image.transitionName =
                "${getString(R.string.restaurant_image_transition)}_${restaurant.id}"
            it.item_restaurant_text_price.text =
                "${getString(R.string.restaurant_text_price)} ${restaurant.value}"
            it.item_restaurant_text_price.transitionName =
                "${getString(R.string.restaurant_text_price_transition)}_${restaurant.id}"
            it.item_restaurant_text_address.text =
                "${getString(R.string.restaurant_text_address)} ${restaurant.address}"
            it.item_restaurant_text_city.text =
                "${getString(R.string.restaurant_text_city)} ${restaurant.city}"
            it.item_restaurant_text_area.text =
                "${getString(R.string.restaurant_text_area)} ${restaurant.area}"
            it.item_restaurant_text_state.text =
                "${getString(R.string.restaurant_text_state)} ${RestaurantViewModel.mapOfStates[restaurant.state]}"
            it.item_restaurant_text_zip.text =
                "${getString(R.string.restaurant_text_zip)} ${restaurant.postalCode}"
            it.item_restaurant_text_phone.text =
                "${getString(R.string.restaurant_text_phone)} ${restaurant.phone}"
            it.item_restaurant_text_coordinates.text =
                "${getString(R.string.restaurant_text_coordinates)} (${restaurant.lat}, ${restaurant.lng})"
            it.item_restaurant_text_web.text =
                "${getString(R.string.restaurant_text_web)} ${restaurant.urlReserve}"
            it.item_restaurant_text_web_mobile.text =
                "${getString(R.string.restaurant_text_web_mobile)} ${restaurant.urlMobileReserve}"

            setFavoriteButton()

            // Click listeners
            it.button_open_maps.setOnClickListener { openMaps() }
            it.button_set_favorite.setOnClickListener { togaeFavorites() }
            it.button_unset_favorite.setOnClickListener { togaeFavorites() }
        }
    }

    private fun setFavoriteButton() {
        view?.let {
            if (mRestaurantDetailViewModel.restaurant.isFavorite) {
                it.button_set_favorite.visibility = View.GONE
                it.button_unset_favorite.visibility = View.VISIBLE
            } else {
                it.button_set_favorite.visibility = View.VISIBLE
                it.button_unset_favorite.visibility = View.GONE
            }
        }
    }

    private fun openMaps() {
        val uri: String = java.lang.String.format(
            Locale.ENGLISH,
            "http://maps.google.com/maps?q=loc:%f,%f",
            args.restaurant.lat,
            args.restaurant.lng

        )
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
        args.restaurant.lng
    }

    private fun togaeFavorites() {
        mRestaurantDetailViewModel.restaurant.isFavorite =
            !mRestaurantDetailViewModel.restaurant.isFavorite
        mRestaurantViewModel.toggleFavorites(mRestaurantDetailViewModel.restaurant)
        setFavoriteButton()
    }

}