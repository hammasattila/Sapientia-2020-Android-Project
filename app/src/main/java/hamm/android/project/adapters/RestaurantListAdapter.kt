package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import coil.ImageLoader
//import coil.load
//import coil.request.CachePolicy
//import coil.request.ImageRequest
//import coil.request.SuccessResult
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.*
import kotlinx.android.synthetic.main.item_restaurant.view.*
import kotlinx.android.synthetic.main.layout_restaurant_actions_basic.view.*
import kotlinx.android.synthetic.main.layout_restaurant_information_basic.view.*

class RestaurantListAdapter(private val listener: Listener) : ListAdapter<Restaurant, RecyclerView.ViewHolder>(Restaurant.DIFF_CALLBACK) {

    private var isLoading: Boolean = false;

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
                        val restaurant = getItem(adapterPosition)
                        restaurant.toggleFavorite()
                        listener.onItemToggleFavorite(restaurant)
                    }
                }
            }
        }
    }

    inner class LoadingHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun onItemClick(v: View, restaurant: Restaurant)
        fun onItemToggleFavorite(restaurant: Restaurant)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when(viewType) {
            VIEW_TYPE_RESTAURANT -> RestaurantHolder(layoutInflater.inflate(R.layout.item_restaurant, viewGroup, false))
            else -> LoadingHolder(layoutInflater.inflate(R.layout.item_loading, viewGroup, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RestaurantHolder -> {
                val restaurant = getItem(position)
                holder.itemView.item_restaurant_text_title.text = restaurant.info.name
                holder.itemView.item_restaurant_image.load(restaurant.info.urlImage)
                restaurant.setBasicTextContent(holder.itemView)
                restaurant.setTransitionNames(holder.itemView)
                restaurant.setFavoriteButton(holder.itemView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            currentList.size -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_RESTAURANT
        }
    }

    override fun getItemCount(): Int {
        // TODO
        return super.getItemCount() + when (true) {
            true -> 1
            false -> 0
        }
    }

    override fun submitList(list: List<Restaurant>?) {
        super.submitList(list)
    }

    companion object {
        val VIEW_TYPE_LOADING = 0
        val VIEW_TYPE_CONTROL = 1
        val VIEW_TYPE_RESTAURANT = 2
    }

}
