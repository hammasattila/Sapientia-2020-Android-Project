package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.getClosestInt
import hamm.android.project.utils.getClosestString

import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: RestaurantRepository

    // Members
    var loading: Boolean = false
        private set

    @Volatile
    var restaurantCount: Int = 0
        private set

    //    val restaurants: MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()
    var restaurants: LiveData<List<Restaurant>>
        private set

    // Filter values
    var country: String? = "US"
        private set
    var state: String? = null
        private set
    var city: String? = null
    var zip: String? = null
    var address: String? = null
    var price: Int? = null
        private set
    var name: String? = null

    // Options
    var page: Int = 1



    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repo = RestaurantRepository(restaurantDao)
//        TODO("INTERNAL SERVER ERROR")
        getRestaurants()
        restaurants = repo.getRestaurantsByFiltersAsync()
    }

    fun setFilters(
        country: String?,
        state: String?
    ): Boolean {
        // TODO("Clean the texts from white characters. For now it does the job.")
        if (!checkFilters(name, address, state, city, zip, country)) {
            return false
        }

        this.country = if (country.isNullOrBlank()) null else country
        this.state = if (state.isNullOrBlank()) null else state
        city = if (city.isNullOrBlank()) null else city
        zip = if (zip.isNullOrBlank()) null else zip
        address = if (address.isNullOrBlank()) null else address
        price = if (price == 0) null else price
        name = if (name.isNullOrBlank()) null else name
        page = 1

        getRestaurants()

        restaurants = repo.getRestaurantsByFiltersAsync(country, state, city, zip, address, price, name)

        return true
    }

    private fun checkFilters(
        name: String?,
        address: String?,
        state: String?,
        city: String?,
        zip: String?,
        country: String?
    ): Boolean {
        if (name.isNullOrBlank() && address.isNullOrBlank() && state.isNullOrBlank() && city.isNullOrBlank() && zip.isNullOrBlank() && country.isNullOrBlank()) {
            return false
        }

        return true
    }

    fun getRestaurants() {
        synchronized(restaurantCount) {
            if (-1 == page) {
                return
            }
        }
        synchronized(this.loading) {
            if (loading) {
                return
            } else {
                loading = true
            }
        }
        viewModelScope.launch {
            val count = repo.getRestaurantsByFiltersSync(
                country = country,
                state = state,
                city = city,
                zip = zip,
                address = address,
                name = name,
                page = page
            )
//          TODO("Handle multiple filters")
            synchronized(restaurantCount) {
                page = count
            }

            loading = false
        }
    }

    fun updateRestaurant(restaurant: Restaurant) {
        viewModelScope.launch {
            repo.updateRestaurantSync(restaurant)
        }
    }

}