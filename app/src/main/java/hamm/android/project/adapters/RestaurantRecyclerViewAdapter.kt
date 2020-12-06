package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
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

class RestaurantRecyclerViewAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_LOADING = 0
        val VIEW_TYPE_CONTROL = 1
        val VIEW_TYPE_RESTAURANT = 2
    }

    private var dataCount: Int = 0
    private var data: List<Restaurant> = listOf()

    inner class RestaurantViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
            view.button_set_favorite.setOnClickListener(this)
            view.button_unset_favorite.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            when (v) {
                is CardView -> {
                    if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                        listener.onItemClick(v, data[adapterPosition])
                    }
                }
                is Button -> {
                    if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                        val restaurant = data[adapterPosition]
                        restaurant.toggleFavorite()
                        listener.toggleFavorite(restaurant)
                    }
                }
            }
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun onItemClick(v: View, restaurant: Restaurant)
        fun toggleFavorite(restaurant: Restaurant)
    }

    fun setData(count: Int, restaurants: List<Restaurant>) {
        dataCount = count
        data = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            VIEW_TYPE_LOADING -> LoadingViewHolder(layoutInflater.inflate(R.layout.item_loading, viewGroup, false))
            else -> RestaurantViewHolder(layoutInflater.inflate(R.layout.item_restaurant, viewGroup, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RestaurantViewHolder) {
            holder.view.item_restaurant_text_title.text = data[position].information.name
            holder.view.item_restaurant_image.load(data[position].information.urlImage)
            data[position].setBasicTextContent(holder.view)

            data[position].setTransitionNames(holder.view)
            data[position].setFavoriteButton(holder.view)
        }
    }

    override fun getItemCount(): Int {
        return data.size + when {
            dataCount <= data.size -> 0
            else -> 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            data.size -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_RESTAURANT
        }
    }
}
