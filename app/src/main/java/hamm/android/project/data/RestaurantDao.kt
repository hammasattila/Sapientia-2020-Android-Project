package hamm.android.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantBase
import hamm.android.project.model.RestaurantExtension

@Dao
interface RestaurantDao {

    @Transaction
    @Query("SELECT * FROM restaurant_table ORDER BY area ASC")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Transaction
    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    fun getRestaurantById(id: Int): LiveData<Restaurant>

    @Transaction
    @Query("SELECT * FROM restaurant_table JOIN restaurant_extension_table ON id = restaurantId WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): LiveData<List<Restaurant>>





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants: List<RestaurantBase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantExt(ext: RestaurantExtension)

    @Update
    suspend fun updateRestaurantExt(ext: RestaurantExtension)
}