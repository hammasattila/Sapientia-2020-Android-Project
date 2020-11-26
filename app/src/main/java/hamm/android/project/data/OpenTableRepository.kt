package hamm.android.project.data

import hamm.android.project.api.RetrofitInstance
import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurants

class OpenTableRepository {
    suspend fun getCities(): Cities {
        return RetrofitInstance.api.getCities()
    }

    suspend fun getRestaurants(state: String): Restaurants {
        return RetrofitInstance.api.getRestaurants(state)
    }
}