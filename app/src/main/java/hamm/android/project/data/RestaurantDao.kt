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
    @Query("SELECT * FROM restaurant_table ORDER BY area ASC")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Transaction
    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    fun getRestaurantById(id: Int): LiveData<Restaurant>

    @Transaction
    @Query("SELECT id, name, address, city, state, area, postalCode, country, phone, lat, lng, price, urlReserve, urlMobileReserve, urlImage FROM restaurant_table JOIN restaurant_extension_table ON id = restaurantId WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): LiveData<List<Restaurant>>

    @Query("SELECT COUNT(*) FROM restaurant_table WHERE id IN (:listOfIds)")
    suspend fun isNewDataInTheListSync(listOfIds: List<Long>): Long

    @Query("SELECT * FROM restaurant_table WHERE country = COALESCE(:country, country) AND state = COALESCE(:state, state) AND city = COALESCE(:city, city) AND postalCode = COALESCE(:zip, postalCode) AND address = COALESCE(:address, address) AND name = COALESCE(:name, name) AND price = COALESCE(:price, price)")
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