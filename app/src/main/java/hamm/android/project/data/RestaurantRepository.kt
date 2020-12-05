package hamm.android.project.data

import androidx.lifecycle.LiveData
import hamm.android.project.api.RetrofitInstance
import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurant
import hamm.android.project.model.Restaurants

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    suspend fun getCitiesSync(): Cities {
        return RetrofitInstance.api.getCities()
    }


    suspend fun getRestaurantsSync(country: String?, state: String?, city: String?, zip: String?, address: String?, name: String?, perPage: Int? = null, page: Int? = null): Restaurants {
        val restaurants = RetrofitInstance.api.getRestaurants(country, state, city, zip, address, name, perPage, page)
        for (i in 0 until restaurants.restaurants.size) {
            val rLocal = restaurantDao.findRestaurantById(restaurants.restaurants[i].id)
            if(null != rLocal) {
                restaurants.restaurants[i] = rLocal
            }
        }

        return restaurants
    }

    suspend fun toggleFavoriteSync(restaurant: Restaurant) {
        restaurantDao.insertRestaurant(restaurant)
    }

    fun getFavoritesAsync(): LiveData<List<Restaurant>> {
        return restaurantDao.getFavorites()
    }
}