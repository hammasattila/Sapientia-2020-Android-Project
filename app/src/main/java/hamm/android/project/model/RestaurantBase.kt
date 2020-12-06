package hamm.android.project.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import hamm.android.project.data.RestaurantRepository
import java.io.Serializable

@Entity(tableName = "restaurant_table")
data class RestaurantBase(
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
    var urlImage: String
) : Serializable {
    val value: String
        get() = "$".repeat(price)

    val stateName: String
        get() = RestaurantRepository.mapOfStates[state] ?: ""

    override fun equals(other: Any?): Boolean {
        return (other is RestaurantBase) &&
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
                urlImage == other.urlImage
    }
}