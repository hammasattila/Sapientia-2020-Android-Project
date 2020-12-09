package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

class RestaurantListAdapter(private val listener: Listener) : ListAdapter<Restaurant, RestaurantListAdapter.RestaurantHolder>(Restaurant.DIFF_CALLBACK) {

    private var isLoading: Boolean = false

    inner class RestaurantHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
            view.findViewById<Button>(R.id.button_set_favorite).setOnClickListener(this)
            view.findViewById<Button>(R.id.button_unset_favorite).setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            when (v) {
                is CardView -> {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        listener.onItemClick(v, getItem(adapterPosition))
                    }
                }
                is Button -> {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        val restaurant = getItem(adapterPosition)
                        restaurant.toggleFavorite()
                        restaurant.setFavoriteButton(v.parent as View)
                        listener.onItemToggleFavorite(restaurant)
                    }
                }
            }
        }
    }

    interface Listener {
        fun onItemClick(v: View, restaurant: Restaurant)
        fun onItemToggleFavorite(restaurant: Restaurant)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RestaurantHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return RestaurantHolder(layoutInflater.inflate(R.layout.item_restaurant, viewGroup, false))
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        val restaurant = getItem(position)
        holder.itemView.findViewById<TextView>(R.id.item_restaurant_text_title).text = restaurant.info.name
        holder.itemView.findViewById<ImageView>(R.id.item_restaurant_image).load(restaurant.getImageUrl())
        restaurant.setBasicTextContent(holder.itemView)
        restaurant.setTransitionNames(holder.itemView)
        restaurant.setFavoriteButton(holder.itemView)
    }

}
