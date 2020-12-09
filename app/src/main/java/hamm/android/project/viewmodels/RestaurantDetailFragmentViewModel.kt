package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.Restaurant
import kotlinx.coroutines.launch

class RestaurantDetailFragmentViewModel(application: Application): AndroidViewModel(application) {
    val repo: RestaurantRepository
    lateinit var restaurant: Restaurant

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repo = RestaurantRepository(restaurantDao)
    }

    fun updateRestaurant() {
        viewModelScope.launch {
            restaurant.ext = repo.updateRestaurantExtSync(restaurant.ext!!)
        }
    }

    fun getRestaurantById(id: Long): LiveData<Restaurant> {
        return repo.getRestaurantByIdAsync(id)
    }
}
