package hamm.android.project.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Restaurant(
    val id: Int,
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
    val urlImage: String
): Serializable {
    val value: String
        get() = "$".repeat(price)
}
