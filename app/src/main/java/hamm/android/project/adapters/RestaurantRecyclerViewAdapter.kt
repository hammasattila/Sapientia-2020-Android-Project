package hamm.android.project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import kotlinx.android.synthetic.main.recycle_view_item_restaurant.view.*

class RestaurantRecyclerViewAdapter (private val listener: Listener) :  RecyclerView.Adapter<RestaurantRecyclerViewAdapter.ViewHolder>(){

    private var data: ArrayList<Restaurant> = ArrayList()

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

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

    interface Listener {
        fun onItemClick(v: View, d: Restaurant)
        fun onItemLongClick()
    }

    fun setData(restaurants: ArrayList<Restaurant>){
        data = restaurants
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val v = layoutInflater.inflate(R.layout.recycle_view_item_restaurant, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.item_restaurant_text_title.text = data[position].name

        holder.view.item_restaurant_image.transitionName = "${holder.itemView.context.getString(R.string.restaurant_image_transition)}_${data[position].id}"

        holder.view.item_restaurant_text_price.text = "${holder.itemView.context.getString(R.string.restaurant_text_price)} ${data[position].value}"
        holder.view.item_restaurant_text_price.transitionName = "${holder.itemView.context.getString(R.string.restaurant_text_price_transition)}_${data[position].id}"
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
