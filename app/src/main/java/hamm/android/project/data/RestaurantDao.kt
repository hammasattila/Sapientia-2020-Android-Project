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