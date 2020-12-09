package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository

class RestaurantListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RestaurantRepository

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
    }
}