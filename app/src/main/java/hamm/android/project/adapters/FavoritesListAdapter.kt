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


class FavoritesListAdapter(val listener: Listener) : ListAdapter<Restaurant, FavoritesListAdapter.RestaurantHolder>(DIFF_CALLBACK) {
    inner class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {
        init {
            view.setOnClickListener(this)
            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                listener.onItemClick(v, getItem(adapterPosition))
            }
        }

        override fun onLongClick(v: View?): Boolean {
            if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                listener.onItemLongClick(v, getItem(adapterPosition))

                return true
            }

            return false
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            else -> RestaurantHolder(layoutInflater.inflate(R.layout.recycle_view_item_restaurant, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        if (holder is RestaurantHolder) {
            val restaurant = getItem(position)
            holder.itemView.item_restaurant_text_title.text = restaurant.name

            holder.itemView.item_restaurant_image.load(restaurant.urlImage)
            holder.itemView.item_restaurant_image.transitionName = "${holder.itemView.context.getString(R.string.restaurant_image_transition)}_${restaurant.id}"

            holder.itemView.item_restaurant_text_price.text = "${holder.itemView.context.getString(R.string.restaurant_text_price)} ${restaurant.value}"
            holder.itemView.item_restaurant_text_price.transitionName =
                "${holder.itemView.context.getString(R.string.restaurant_text_price_transition)}_${restaurant.id}"
        }
    }


    interface Listener {
        fun onItemClick(element: View, restaurant: Restaurant)
        fun onItemLongClick(element: View, restaurant: Restaurant)
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