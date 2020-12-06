package hamm.android.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantBase
import hamm.android.project.model.RestaurantExtension

@Dao
interface RestaurantDao {

    @Transaction
    @Query("SELECT * FROM restaurant_table")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRestaurants(restaurants: List<RestaurantBase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurantExt(ext: RestaurantExtension)

    @Update
    suspend fun updateRestaurantExt(ext: RestaurantExtension)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertRestaurant(restaurant: Restaurant)

//    @Query("SELECT * FROM restaurant_table WHERE id = :id")
//    suspend fun findRestaurantById(id: Int): Restaurant?

//    @Query("SELECT * FROM restaurant_table WHERE isFavorite = 1 ORDER BY NAME ASC")
//    fun getFavorites() :LiveData<List<Restaurant>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun toggleFavorite(food: Restaurant)
//
//    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
//    fun getAllFoods(): LiveData<List<Food>>

//    @Query("SELECT * FROM restaurant_table WHERE id = :id")
//    suspend fun getFood(id: Int): Restaurant
}