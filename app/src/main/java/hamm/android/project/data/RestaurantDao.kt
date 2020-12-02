package hamm.android.project.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import hamm.android.project.model.Restaurant

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    suspend fun findRestaurantById(id: Int): Restaurant?

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun toggleFavorite(food: Restaurant)
//
//    @Query("SELECT * FROM restaurant_table ORDER BY name ASC")
//    fun getAllFoods(): LiveData<List<Food>>

//    @Query("SELECT * FROM restaurant_table WHERE id = :id")
//    suspend fun getFood(id: Int): Restaurant
}