package hamm.android.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantBase
import hamm.android.project.model.RestaurantPhoto
import hamm.android.project.model.RestaurantUserData
import hamm.android.project.utils.SELECTION

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

    @Query("SELECT COUNT(*) FROM restaurant_table WHERE id IN (:listOfIds) AND $SELECTION")
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

    @Query("SELECT * FROM restaurant_table WHERE $SELECTION ORDER BY page ASC")
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