package hamm.android.project.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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
    val urlImage: String,
    var isFavorite: Boolean = false,
) : Serializable {
    val value: String
        get() = "$".repeat(price)
}
