package hamm.android.project.api


import hamm.android.project.model.Cities
import hamm.android.project.model.Countries
import hamm.android.project.model.Restaurants
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenTableAPI {

    @GET("cities")
    suspend fun getCities(): Cities

    @GET("countries")
    suspend fun getCountries(): Countries

    @GET("restaurants")
    suspend fun getRestaurants(
        @Query("country") country: String?,
        @Query("state") state: String?,
        @Query("city") city: String?,
        @Query("zip") zip: String?,
        @Query("address") address: String?,
        @Query("name") name: String?,
        @Query("per_page") perPage: Int?,
        @Query("page") page: Int?,
    ): Restaurants
}