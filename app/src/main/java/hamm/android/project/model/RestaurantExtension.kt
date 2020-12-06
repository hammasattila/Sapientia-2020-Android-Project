package hamm.android.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "restaurant_extension_table")
data class RestaurantExtension(
    val restaurantId: Int,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "extensionId") val id: Int = 0,
    var isFavorite: Boolean = false
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return (other is RestaurantExtension) &&
                id == other.id &&
                restaurantId == other.restaurantId &&
                isFavorite == other.isFavorite
    }
}