package hamm.android.project.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_extension_table")
data class RestaurantExtension(
    val restaurantId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var isFavorite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return (other is RestaurantExtension) &&
                id == other.id &&
                restaurantId == other.restaurantId &&
                isFavorite == other.isFavorite
    }
}