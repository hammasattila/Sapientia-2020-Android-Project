package hamm.android.project.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import androidx.room.Relation
// TODO(Remove Serializable)
import java.io.Serializable

data class Restaurant(
    @Embedded val info: RestaurantBase,
    @Relation(parentColumn = "id", entityColumn = "restaurantId", entity = RestaurantUserData::class)
    var ext: RestaurantExtension?
) : Serializable {

    override fun equals(other: Any?): Boolean {
        return other is Restaurant && info == other.info && ext == other.ext
    }

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.info.id == newItem.info.id
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

    }
}

