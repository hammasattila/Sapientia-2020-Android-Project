package hamm.android.project.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "restaurant_image_table")
data class RestaurantPhoto (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "imageId") val id: Long = 0,
    var extensionId: Long,
    val imageUrl: String
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return (other is RestaurantPhoto) &&
                id == other.id &&
                extensionId == other.extensionId &&
                imageUrl == other.imageUrl
    }
}