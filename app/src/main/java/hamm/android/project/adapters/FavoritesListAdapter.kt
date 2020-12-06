package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.load
import hamm.android.project.utils.setBasicTextContent
import hamm.android.project.utils.setFavoriteButton
import hamm.android.project.utils.setTransitionNames
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlinx.android.synthetic.main.layout_restaurant_actions_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_basic.view.*


class FavoritesListAdapter(val listener: Listener) : ListAdapter<Restaurant, FavoritesListAdapter.RestaurantHolder>(Restaurant.DIFF_CALLBACK) {
    inner class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
            view.button_set_favorite.setOnClickListener(this)
            view.button_unset_favorite.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v) {
                is CardView -> {
                    if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                        listener.onItemClick(v, getItem(adapterPosition))
                    }
                }
                is Button -> {
                    if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                        listener.onToggleFavorite(v, getItem(adapterPosition))
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            else -> RestaurantHolder(layoutInflater.inflate(R.layout.item_restaurant, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        if (holder is RestaurantHolder) {
            val restaurant = getItem(position)
            holder.itemView.item_restaurant_text_title.text = restaurant.info.name
            holder.itemView.item_restaurant_image.load(restaurant.info.urlImage)
            restaurant.setBasicTextContent(holder.itemView)
            restaurant.setTransitionNames(holder.itemView)
            restaurant.setFavoriteButton(holder.itemView)
        }
    }


    interface Listener {
        fun onItemClick(element: View, restaurant: Restaurant)
        fun onToggleFavorite(element: View, restaurant: Restaurant)
    }
}