package hamm.android.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantBase
import hamm.android.project.model.RestaurantPhoto
import hamm.android.project.model.RestaurantUserData

@Dao
interface RestaurantDao {

    @Transaction
    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    fun getRestaurantById(id: Long): LiveData<Restaurant>

    @Query("DELETE FROM restaurant_table WHERE id NOT IN (SELECT id FROM restaurant_extension_table)")
    suspend fun deleteUnnecessaryRestaurants()

    @Transaction
    @Query("SELECT id, name, address, city, state, area, postalCode, country, phone, lat, lng, price, urlReserve, urlMobileReserve, urlImage, page FROM restaurant_table JOIN restaurant_extension_table ON id = restaurantId WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): LiveData<List<Restaurant>>

    @Query("SELECT COUNT(*) FROM restaurant_table WHERE id IN (:listOfIds) AND country = COALESCE(:country, country) AND state = COALESCE(:state, state) AND city = COALESCE(:city, city) AND postalCode = COALESCE(:zip, postalCode) AND address = COALESCE(:address, address) AND name = COALESCE(:name, name) AND price = COALESCE(:price, price)")
    suspend fun isNewDataInTheListForAppliedFilterSync(
        listOfIds: List<Long>,
        country: String? = null,
        state: String? = null,
        city: String? = null,
        zip: String? = null,
        address: String? = null,
        price: Int? = null,
        name: String? = null
    ): Long

    @Query("SELECT * FROM restaurant_table WHERE country = COALESCE(:country, country) AND state = COALESCE(:state, state) AND city = COALESCE(:city, city) AND postalCode = COALESCE(:zip, postalCode) AND address = COALESCE(:address, address) AND name = COALESCE(:name, name) AND price = COALESCE(:price, price) ORDER BY page ASC")
    fun getRestaurantsByFiltersAsync(
        country: String? = null,
        state: String? = null,
        city: String? = null,
        zip: String? = null,
        address: String? = null,
        price: Int? = null,
        name: String? = null
    ): LiveData<List<Restaurant>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants: List<RestaurantBase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantUserData(ext: RestaurantUserData): Long

    @Update
    suspend fun updateRestaurantUserData(ext: RestaurantUserData)

    @Insert
    suspend fun insertRestaurantImage(image: RestaurantPhoto): Long

    @Delete
    suspend fun deleteRestaurantImage(image: RestaurantPhoto)


}