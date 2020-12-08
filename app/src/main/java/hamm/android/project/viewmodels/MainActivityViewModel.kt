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
    val restaurants: LiveData<List<Restaurant>>

    // Filter values
    var country: String? = "US"
        private set
    var state: String? = null
        private set
    var city: String? = null
    var zip: String? = null
    var address: String? = null
    var name: String? = null

    // Options
    var page: Int = 1
    var perPage: Int = RestaurantRepository.numberOfRestaurantsPerPage[0]
        private set(perPage) {
            field = when (RestaurantRepository.numberOfRestaurantsPerPage.contains(perPage)) {
                true -> perPage
                else -> getClosestInt(perPage, RestaurantRepository.numberOfRestaurantsPerPage)
            }
        }


    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repo = RestaurantRepository(restaurantDao)
        getRestaurants()
        restaurants = repo.getAllRestaurantsAsync()
    }

    fun curateCountry(c: String?): String? {
        if (c.isNullOrBlank()) {
            return null
        }

        return getClosestString(c, RestaurantRepository.countries)
    }

    fun curateState(s: String?): String? {
        if (s.isNullOrBlank()) {
            return null
        }

        return getClosestString(s, RestaurantRepository.states)
    }

    @Deprecated("It is useless. The api will return at non complete queries as well.")
    fun curateCity(c: String?): String? {
        if (c.isNullOrBlank()) {
            return null
        }

        return getClosestString(c, RestaurantRepository.cities)
    }

    fun setFilters(
        country: String?,
        state: String?,
        perPage: Int = this.perPage
    ): Boolean {
        // TODO("Clean the texts from white characters. For now it does the job.")
        if (!checkFilters(name, address, state, city, zip, country)) {
            return false
        }

        this.country = country
        this.state = state
        this.perPage = perPage
        this.page = 1

        getRestaurants()

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
//        TODO(Handle paging)
        if (-1 == page) {
            return
        }
        synchronized(this.loading) {
            if (loading) {
                return
            } else {
                loading = true
            }
        }
        viewModelScope.launch {
            val count = repo.getRestaurantsSync(
                country = country,
                state = state,
                city = city,
                zip = zip,
                address = address,
                name = name,
                page = page,
                perPage = perPage
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