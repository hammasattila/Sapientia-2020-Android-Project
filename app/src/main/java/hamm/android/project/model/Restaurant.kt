package hamm.android.project.model

import android.view.View
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import hamm.android.project.R
import kotlinx.android.synthetic.main.layout_restaurant_actions_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_actions_detailed.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_basic.view.*
import java.io.Serializable

@Entity(tableName = "restaurant_table")
data class Restaurant(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val state: String,
    val area: String,
    @SerializedName("postal_code")
    val postalCode: String,
    val country: String,
    val phone: String,
    val lat: Float,
    val lng: Float,
    val price: Int,
    @SerializedName("reserve_url")
    val urlReserve: String,
    @SerializedName("mobile_reserve_url")
    val urlMobileReserve: String,
    @SerializedName("image_url")
    var urlImage: String,
    var isFavorite: Boolean = false,
) : Serializable {
    val value: String
        get() = "$".repeat(price)

    fun transitionExtras(v: View): FragmentNavigator.Extras {
        return FragmentNavigatorExtras(
            v.item_restaurant_image to "restaurant_image_${id}",
            v.item_restaurant_text_price to "restaurant_text_price_${id}",
            v.item_restaurant_text_address to "restaurant_text_address_${id}",
            v.button_set_favorite to "restaurant_button_set_favorite_${id}",
            v.button_unset_favorite to "restaurant_button_unset_favorite_${id}"
        )
    }

    fun setTransitionNames(v: View?) {
        v?.item_restaurant_image?.transitionName = "restaurant_image_${id}"
        v?.item_restaurant_text_price?.transitionName = "restaurant_text_price_${id}"
        v?.item_restaurant_text_address?.transitionName = "restaurant_text_address_${id}"
        v?.button_set_favorite?.transitionName = "restaurant_button_set_favorite_${id}"
        v?.button_unset_favorite?.transitionName = "restaurant_button_unset_favorite_${id}"
    }

    fun setBasicTextContent(v: View?) {
        v?.item_restaurant_text_price?.text = "${v?.context?.getString(R.string.restaurant_text_price)} $price"
        v?.item_restaurant_text_address?.text = "${v?.context?.getString(R.string.restaurant_text_address)} $address"
    }

    fun setFavoriteButton(v: View?) {
        when (isFavorite) {
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

    fun setDetailActions(v: View?) {
        v?.button_set_favorite?.shrink()
        v?.button_unset_favorite?.shrink()
        v?.button_unset_photo?.visibility = View.GONE
    }


    override fun equals(other: Any?): Boolean {
        return (other is Restaurant) &&
                id == other.id &&
                name == other.name &&
                city == other.city &&
                state == other.state &&
                area == other.area &&
                postalCode == other.postalCode &&
                country == other.country &&
                phone == other.phone &&
                lat == other.lat &&
                lng == other.lng &&
                price == other.price &&
                urlReserve == other.urlReserve &&
                urlMobileReserve == other.urlMobileReserve &&
                urlImage == other.urlImage &&
                isFavorite == other.isFavorite
    }
}
