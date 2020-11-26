package hamm.android.project.api


import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurants
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTableAPI {

    @GET("cities")
    suspend fun getCities(): Cities

    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("state") state: String
    ): Restaurants
}