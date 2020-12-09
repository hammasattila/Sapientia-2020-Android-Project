package hamm.android.project.model

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class RestaurantExtension(
    @Embedded val userData: RestaurantUserData,
    @Relation(parentColumn = "extensionId", entityColumn = "extensionId")
    val images: MutableList<RestaurantPhoto> = mutableListOf()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        return other is RestaurantExtension && userData == other.userData && images == other.images
    }
}