package hamm.android.project.viewmodels

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.Restaurant
import hamm.android.project.model.User
import hamm.android.project.utils.IMAGE_REQUEST_CODE
import hamm.android.project.utils.toggleFavorite
import kotlinx.coroutines.launch

class ProfileFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: RestaurantRepository
    val favoriteRestaurants: LiveData<List<Restaurant>>
    var settings: SharedPreferences? = null
        private set
    lateinit var user: User

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repo = RestaurantRepository(restaurantDao)
        favoriteRestaurants = repo.getFavoritesAsync()
    }

    fun setSettings(settings: SharedPreferences?, callback: () -> Unit = {}) {
        settings?.let {
            this.settings = settings
            user = User(
                settings.getString("user_name", "") ?: "",
                settings.getString("user_address", "") ?: "",
                settings.getString("user_phone", "") ?: "",
                settings.getString("user_email", "") ?: "",
                settings.getString("user_image", "") ?: "",
            )

            callback()
        }
    }

    fun saveProfile() {
        settings?.let {
            val editor: SharedPreferences.Editor = it.edit()
            editor.putString("user_name", user.name)
            editor.putString("user_address", user.address)
            editor.putString("user_phone", user.phone)
            editor.putString("user_email", user.email)
            editor.putString("user_image", user.image)
            editor.commit()
        }
    }

    fun toggleFavorite(restaurant: Restaurant) {
        restaurant.toggleFavorite()
        viewModelScope.launch {
            repo.updateRestaurantSync(restaurant)
        }
    }
}