package hamm.android.project.data

import hamm.android.project.api.RetrofitInstance
import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurants

class RestaurantRepository {
    suspend fun getCities(): Cities {
        return RetrofitInstance.api.getCities()
    }

    suspend fun getRestaurants(country: String?, state: String?, city: String?, zip: String?, address: String?, name: String?, perPage: Int? = null, page: Int? = null): Restaurants {
        return RetrofitInstance.api.getRestaurants(country, state, city, zip, address, name, perPage, page)
    }
}