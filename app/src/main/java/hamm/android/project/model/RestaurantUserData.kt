package hamm.android.project.model

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "restaurant_extension_table")
data class RestaurantUserData(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "extensionId") val id: Long = 0,
    val restaurantId: Long,
    var isFavorite: Boolean = false,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return (other is RestaurantUserData) &&
                id == other.id &&
                restaurantId == other.restaurantId &&
                isFavorite == other.isFavorite
    }
}