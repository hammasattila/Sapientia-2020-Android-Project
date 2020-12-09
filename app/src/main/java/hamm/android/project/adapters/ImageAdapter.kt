package hamm.android.project.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import hamm.android.project.R
import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantPhoto
import hamm.android.project.utils.*

class ImageAdapter(val listener: Listener) : ListAdapter<RestaurantPhoto, ImageAdapter.ImageHolder>(DIFF_CALLBACK) {

    inner class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView = view.findViewById<ImageView>(R.id.image_view_restaurant_image)
        var imageUrl: String? = null
            set(url) {
                field = url
                imageView.load(getItem(adapterPosition).imageUrl)
            }

        init {
            view.findViewById<ExtendedFloatingActionButton>(R.id.floating_button_remove_image)
                .setOnClickListener { listener.onImageRemove(getItem(adapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ImageHolder(layoutInflater.inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.imageUrl = getItem(position).imageUrl
    }

    interface Listener {
        fun onImageRemove(image: RestaurantPhoto)
    }

    companion object {
        object DIFF_CALLBACK : DiffUtil.ItemCallback<RestaurantPhoto>() {
            override fun areItemsTheSame(oldItem: RestaurantPhoto, newItem: RestaurantPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RestaurantPhoto, newItem: RestaurantPhoto): Boolean {
                return oldItem == newItem
            }
        }
    }
}