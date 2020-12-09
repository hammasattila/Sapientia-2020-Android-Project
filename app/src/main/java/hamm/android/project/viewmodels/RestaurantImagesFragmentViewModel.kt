package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.RestaurantPhoto
import kotlinx.coroutines.launch

class RestaurantImagesFragmentViewModel(application: Application): AndroidViewModel(application) {

    private val repo: RestaurantRepository
    val removedImages: MutableList<RestaurantPhoto> = mutableListOf()
    lateinit var images: List<RestaurantPhoto>

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repo = RestaurantRepository(restaurantDao)
    }

    fun removeImage(image: RestaurantPhoto) {
        removedImages.add(image)
        viewModelScope.launch {
            repo.deleteImage(image)
        }
    }
}