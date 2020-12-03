package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantDatabase
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.getClosestInt
import hamm.android.project.utils.getClosestString

import kotlinx.coroutines.launch

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RestaurantRepository

    // Constants
    companion object {
        val numberOfRestaurantsPerPage = listOf(5, 10, 15, 25, 50, 100)
        val countries = listOf(
            "AE",
            "AW",
            "CA",
            "CH",
            "CN",
            "CR",
            "GP",
            "HK",
            "KN",
            "KY",
            "MC",
            "MO",
            "MX",
            "MY",
            "PT",
            "SA",
            "SG",
            "SV",
            "US",
            "VI"
        )
        val mapOfStates = mapOf(
            "AL" to "Alabama",
            "AK" to "Alaska",
            "AZ" to "Arizona",
            "AR" to "Arkansas",
            "CA" to "California",
            "CO" to "Colorado",
            "CT" to "Connecticut",
            "DE" to "Delaware",
            "FL" to "Florida",
            "GA" to "Georgia",
            "HI" to "Hawaii",
            "ID" to "Idaho",
            "IL" to "Illinois",
            "IN" to "Indiana",
            "IA" to "Iowa",
            "KS" to "Kansas",
            "KY" to "Kentucky",
            "LA" to "Louisiana",
            "ME" to "Maine",
            "MD" to "Maryland",
            "MA" to "Massachusetts",
            "MI" to "Michigan",
            "MN" to "Minnesota",
            "MS" to "Mississippi",
            "MO" to "Missouri",
            "MT" to "Montana",
            "NE" to "Nebraska",
            "NV" to "Nevada",
            "NH" to "New Hampshire",
            "NJ" to "New Jersey",
            "NM" to "New Mexico",
            "NY" to "New York",
            "NC" to "North Carolina",
            "ND" to "North Dakota",
            "OH" to "Ohio",
            "OK" to "Oklahoma",
            "OR" to "Oregon",
            "PA" to "Pennsylvania",
            "RI" to "Rhode Island",
            "SC" to "South Carolina",
            "SD" to "South Dakota",
            "TN" to "Tennessee",
            "TX" to "Texas",
            "UT" to "Utah",
            "VT" to "Vermont",
            "VA" to "Virginia",
            "WA" to "Washington",
            "WV" to "West Virginia",
            "WI" to "Wisconsin",
            "WY" to "Wyoming"
        )
        val states = mapOfStates.values.toList()

        fun getStateCode(state: String?): String? {
            if (state.isNullOrBlank()) {
                return null
            }
            try {
                return mapOfStates.filterValues { it == state }.keys.elementAt(0)
            } catch (e: IndexOutOfBoundsException) {
                return null
            }
        }
    }

    lateinit var cities: List<String>
        private set

    // Members
    @Volatile
    var loading: Boolean = true
        private set
    val restaurants: MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()

    // Filter values
    var country: String? = "US"
        private set
    var state: String? = null
        private set
    var city: String? = null
        private set
    var zip: String? = null
        private set
    var address: String? = null
        private set
    var name: String? = null
        private set

    // Options
    var page: Int = 1
    var perPage: Int = 50
        private set(perPage) {
            field = when (numberOfRestaurantsPerPage.contains(perPage)) {
                true -> perPage
                else -> getClosestInt(perPage, numberOfRestaurantsPerPage)
            }
        }


    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
        loadRestaurants()
        getCities()
    }

    fun curateCountry(c: String): String? {
        if (c.isNullOrBlank()) {
            return null
        }

        return getClosestString(c, countries)
    }

    fun curateState(s: String): String? {
        if (s.isNullOrBlank()) {
            return null
        }

        return getClosestString(s, states)
    }

    @Deprecated("It is useless. The api will return at non complete queries as well.")
    fun curateCity(c: String): String? {
        if (c.isNullOrBlank()) {
            return null
        }

        return getClosestString(c, cities)
    }

    fun setFilters(
        country: String?,
        state: String?,
        city: String?,
        zip: String?,
        address: String?,
        name: String?,
        perPage: Int = this.perPage
    ): Boolean {
        // TODO("Clean the texts from white characters. For now it does the job.")
        if (!checkFilters(name, address, state, city, zip, country)) {
            return false
        }

        this.country = country
        this.state = state
        this.city = city
        this.zip = zip
        this.address = address
        this.name = name
        this.perPage = perPage
        this.page = 1

        loadRestaurants()

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

    private fun loadRestaurants() {
        synchronized(this.loading) {
            if (!loading) {
                loading = true
            }
        }
        viewModelScope.launch {
            val response = repository.getRestaurants(
                country = country,
                state = state,
                city = city,
                zip = zip,
                address = address,
                name = name,
                page = page,
                perPage = perPage
            )

            synchronized(restaurants) {
                restaurants.value = response.restaurants
            }
            loading = false
        }
    }

    fun getRestaurants() {
        synchronized(this.loading) {
            if (!loading) {
                loading = true
            }
        }
        ++page
        viewModelScope.launch {
            val response = repository.getRestaurants(
                country = country,
                state = state,
                city = city,
                zip = zip,
                address = address,
                name = name,
                page = page,
                perPage = perPage
            )

            synchronized(restaurants) {
                val tmp: ArrayList<Restaurant> = restaurants.value ?: ArrayList()
                tmp.addAll(response.restaurants)
                restaurants.value = tmp
            }
            loading = false
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            val response = repository.getCities()
            cities = response.cities
        }
    }

    fun toggleFavorites(restaurant: Restaurant) {
        restaurants.value?.let {
            for (i in 0 until it.size) {
                if (restaurant.id == it[i].id) {
                    it[i] = restaurant
                }
            }
            viewModelScope.launch {
                repository.toggleFavorite(restaurant)
            }
        }
    }

}