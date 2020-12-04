package hamm.android.project.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
//import coil.ImageLoader
//import coil.load
//import coil.request.CachePolicy
//import coil.request.ImageRequest
//import coil.request.SuccessResult
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.load
import kotlinx.android.synthetic.main.recycle_view_item_restaurant.view.*

class RestaurantRecyclerViewAdapter(private val listener: Listener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_LOADING = 0
        val VIEW_TYPE_CONTROL = 1
        val VIEW_TYPE_RESTAURANT = 2
    }

    private var dataCount: Int = 0
    private var data: ArrayList<Restaurant> = ArrayList()

    inner class RestaurantViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

        init {
            view.setOnClickListener(this)
//            view.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION && v != null) {
                listener.onItemClick(v, data[adapterPosition])
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemLongClick()
                return true
            }

            return false
        }
    }

    inner class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface Listener {
        fun onItemClick(v: View, d: Restaurant)
        fun onItemLongClick()
    }

    fun setData(count: Int, restaurants: ArrayList<Restaurant>) {
        dataCount = count
        data = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            VIEW_TYPE_LOADING -> LoadingViewHolder(layoutInflater.inflate(R.layout.recycle_view_item_loading, viewGroup, false))
            else -> RestaurantViewHolder(layoutInflater.inflate(R.layout.recycle_view_item_restaurant, viewGroup, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RestaurantViewHolder) {
            holder.view.item_restaurant_text_title.text = data[position].name

            holder.view.item_restaurant_image.load(data[position].urlImage)
            holder.view.item_restaurant_image.transitionName = "${holder.itemView.context.getString(R.string.restaurant_image_transition)}_${data[position].id}"

            holder.view.item_restaurant_text_price.text = "${holder.itemView.context.getString(R.string.restaurant_text_price)} ${data[position].value}"
            holder.view.item_restaurant_text_price.transitionName =
                "${holder.itemView.context.getString(R.string.restaurant_text_price_transition)}_${data[position].id}"
        }
    }

    override fun getItemCount(): Int {
        return data.size + when{
            dataCount <= data.size -> 0
            else -> 1}
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            data.size -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_RESTAURANT
        }
    }
}
