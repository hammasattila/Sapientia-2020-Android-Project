package hamm.android.project.utils

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantExtension


fun Restaurant.transitionExtras(v: View): FragmentNavigator.Extras {
    return FragmentNavigatorExtras(
        v.findViewById<ImageView>(R.id.item_restaurant_image) to "restaurant_image_${info.id}",
        v.findViewById<TextView>(R.id.item_restaurant_text_price) to "restaurant_text_price_${info.id}",
        v.findViewById<TextView>(R.id.item_restaurant_text_address) to "restaurant_text_address_${info.id}",
        v.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite) to "restaurant_button_set_favorite_${info.id}",
        v.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite) to "restaurant_button_unset_favorite_${info.id}"
    )
}

fun Restaurant.setTransitionNames(v: View?) {
    v?.findViewById<ImageView>(R.id.item_restaurant_image)?.transitionName = "restaurant_image_${info.id}"
    v?.findViewById<TextView>(R.id.item_restaurant_text_price)?.transitionName = "restaurant_text_price_${info.id}"
    v?.findViewById<TextView>(R.id.item_restaurant_text_address)?.transitionName = "restaurant_text_address_${info.id}"
    v?.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite)?.transitionName = "restaurant_button_set_favorite_${info.id}"
    v?.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite)?.transitionName = "restaurant_button_unset_favorite_${info.id}"
}

fun Restaurant.setBasicTextContent(v: View?) {
    v?.findViewById<TextView>(R.id.item_restaurant_text_price)?.text = "${v?.context?.getString(R.string.restaurant_text_price)} ${info.price}"
    v?.findViewById<TextView>(R.id.item_restaurant_text_address)?.text = "${v?.context?.getString(R.string.restaurant_text_address)} ${info.address}"
}

fun Restaurant.setFavoriteButton(v: View?) {
    when (ext?.isFavorite ?: false) {
        true -> {
            v?.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite)?.visibility = View.GONE
            v?.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite)?.visibility = View.VISIBLE
        }
        false -> {
            v?.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite)?.visibility = View.VISIBLE
            v?.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite)?.visibility = View.GONE
        }
    }
}

fun Restaurant.setDetailActions(v: View?) {
    v?.findViewById<ExtendedFloatingActionButton>(R.id.button_set_favorite)?.shrink()
    v?.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_favorite)?.shrink()
    v?.findViewById<ExtendedFloatingActionButton>(R.id.button_unset_photo)?.visibility = View.GONE
}

fun Restaurant.toggleFavorite() {
    if(ext == null) {
        ext = RestaurantExtension(restaurantId = info.id)
    }

    ext!!.isFavorite = !ext!!.isFavorite
}