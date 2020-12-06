package hamm.android.project.utils

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantExtension
import kotlinx.android.synthetic.main.layout_restaurant_actions_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_actions_detailed.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_basic.view.*

fun Restaurant.transitionExtras(v: View): FragmentNavigator.Extras {
    return FragmentNavigatorExtras(
        v.item_restaurant_image to "restaurant_image_${information.id}",
        v.item_restaurant_text_price to "restaurant_text_price_${information.id}",
        v.item_restaurant_text_address to "restaurant_text_address_${information.id}",
        v.button_set_favorite to "restaurant_button_set_favorite_${information.id}",
        v.button_unset_favorite to "restaurant_button_unset_favorite_${information.id}"
    )
}

fun Restaurant.setTransitionNames(v: View?) {
    v?.item_restaurant_image?.transitionName = "restaurant_image_${information.id}"
    v?.item_restaurant_text_price?.transitionName = "restaurant_text_price_${information.id}"
    v?.item_restaurant_text_address?.transitionName = "restaurant_text_address_${information.id}"
    v?.button_set_favorite?.transitionName = "restaurant_button_set_favorite_${information.id}"
    v?.button_unset_favorite?.transitionName = "restaurant_button_unset_favorite_${information.id}"
}

fun Restaurant.setBasicTextContent(v: View?) {
    v?.item_restaurant_text_price?.text = "${v?.context?.getString(R.string.restaurant_text_price)} ${information.price}"
    v?.item_restaurant_text_address?.text = "${v?.context?.getString(R.string.restaurant_text_address)} ${information.address}"
}

fun Restaurant.setFavoriteButton(v: View?) {
    when (ext?.isFavorite ?: false) {
        true -> {
            v?.button_set_favorite?.visibility = View.GONE
            v?.button_unset_favorite?.visibility = View.VISIBLE
        }
        false -> {
            v?.button_set_favorite?.visibility = View.VISIBLE
            v?.button_unset_favorite?.visibility = View.GONE
        }
    }
}

fun Restaurant.setDetailActions(v: View?) {
    v?.button_set_favorite?.shrink()
    v?.button_unset_favorite?.shrink()
    v?.button_unset_photo?.visibility = View.GONE
}

fun Restaurant.toggleFavorite() {
    if(ext == null) {
        ext = RestaurantExtension(restaurantId = information.id)
    }

    ext!!.isFavorite = !ext!!.isFavorite
}