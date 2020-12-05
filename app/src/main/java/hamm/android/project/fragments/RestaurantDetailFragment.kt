package hamm.android.project.fragments

//import coil.Coil
//import coil.ImageLoader
//import coil.load
//import coil.memory.MemoryCache
//import coil.request.CachePolicy
//import coil.request.ImageRequest
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
import hamm.android.project.utils.load
import hamm.android.project.viewmodels.RestaurantDetailViewModel
import hamm.android.project.viewmodels.RestaurantViewModel
import hamm.android.project.viewmodels.RestaurantViewModelFactory
import kotlinx.android.synthetic.main.layout_restaurant_actions_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_actions_detailed.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_detailed.view.*
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

            postponeEnterTransition()
            it.item_restaurant_image.load(restaurant.urlImage) { startPostponedEnterTransition() }
            restaurant.setBasicTextContent(it)
            it.item_restaurant_text_city.text = "${getString(R.string.restaurant_text_city)} ${restaurant.city}"
            it.item_restaurant_text_area.text = "${getString(R.string.restaurant_text_area)} ${restaurant.area}"
            it.item_restaurant_text_state.text = "${getString(R.string.restaurant_text_state)} ${RestaurantViewModel.mapOfStates[restaurant.state]}"
            it.item_restaurant_text_zip.text = "${getString(R.string.restaurant_text_zip)} ${restaurant.postalCode}"
            it.item_restaurant_text_phone.text = "${getString(R.string.restaurant_text_phone)} ${restaurant.phone}"
            it.item_restaurant_text_coordinates.text = "${getString(R.string.restaurant_text_coordinates)} (${restaurant.lat}, ${restaurant.lng})"
            it.item_restaurant_text_web.text = "${getString(R.string.restaurant_text_web)} ${restaurant.urlReserve}"
            it.item_restaurant_text_web_mobile.text = "${getString(R.string.restaurant_text_web_mobile)} ${restaurant.urlMobileReserve}"

            restaurant.setTransitionNames(it)
            restaurant.setFavoriteButton(it)
            restaurant.setDetailActions(it)

            // Click listeners
            it.button_open_maps.setOnClickListener { openMaps() }
            it.button_open_phone.setOnClickListener { dialNumber() }
            it.button_set_favorite.setOnClickListener { togaeFavorites() }
            it.button_unset_favorite.setOnClickListener { togaeFavorites() }
        }
    }

    private fun dialNumber() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:${mRestaurantDetailViewModel.restaurant.phone}")))
    }

    private fun openMaps() {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?q=loc:${mRestaurantDetailViewModel.restaurant.lat},${mRestaurantDetailViewModel.restaurant.lng}")
            )
        )
    }

    private fun togaeFavorites() {
        mRestaurantDetailViewModel.restaurant.isFavorite = !mRestaurantDetailViewModel.restaurant.isFavorite
        mRestaurantViewModel.toggleFavorites(mRestaurantDetailViewModel.restaurant)
        mRestaurantDetailViewModel.restaurant.setFavoriteButton(view)
    }

}