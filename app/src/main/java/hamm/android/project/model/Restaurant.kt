package hamm.android.project.model

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class Restaurant(
    @Embedded val information: RestaurantBase,
    @Relation(parentColumn = "id", entityColumn = "restaurantId")
    var ext: RestaurantExtension?
): Serializable

