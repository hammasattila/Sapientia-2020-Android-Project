package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.load
import kotlinx.android.synthetic.main.recycle_view_item_restaurant.view.*


class FavoriteListAdapter() : ListAdapter<Restaurant, FavoriteListAdapter.RestaurantHolder>(DIFF_CALLBACK) {
    inner class RestaurantHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            else -> RestaurantHolder(layoutInflater.inflate(R.layout.recycle_view_item_restaurant, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        if (holder is FavoriteListAdapter.RestaurantHolder) {
            val restaurant = getItem(position)
            holder.view.item_restaurant_text_title.text = restaurant.name

            holder.view.item_restaurant_image.load(restaurant.urlImage)
            holder.view.item_restaurant_image.transitionName = "${holder.itemView.context.getString(R.string.restaurant_image_transition)}_${restaurant.id}"

            holder.view.item_restaurant_text_price.text = "${holder.itemView.context.getString(R.string.restaurant_text_price)} ${restaurant.value}"
            holder.view.item_restaurant_text_price.transitionName =
                "${holder.itemView.context.getString(R.string.restaurant_text_price_transition)}_${restaurant.id}"
        }
    }


    companion object {
        private object DIFF_CALLBACK : DiffUtil.ItemCallback<Restaurant>() {
            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }

        }
    }
}