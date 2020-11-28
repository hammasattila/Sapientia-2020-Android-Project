package hamm.android.project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.RestaurantRepository
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.Constants
import hamm.android.project.utils.Helpers
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repository: RestaurantRepository) : ViewModel() {

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

        fun getStateCode(state: String): String? {
            var res: String? = null
            try {
                if (state.isNotBlank()) {
                    res = mapOfStates.filterValues { it == state }.keys.elementAt(0)
                }
            } catch (e: IndexOutOfBoundsException) {
            } finally {
                return res
            }
        }
    }

    lateinit var cities: List<String>
        private set

    // Members
    val restaurants: MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()

    // Filter values
    var country: String? = "US"
        private set
    var state: String? = null
        set(state) {
            synchronized(this) {
                field = when (mapOfStates.containsKey(state)) {
                    true -> state
                    else -> field
                }
                getRestaurants()
            }
        }
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
    var perPage: Int = numberOfRestaurantsPerPage[0]
        set(perPage) {
            field = when (numberOfRestaurantsPerPage.contains(perPage)) {
                true -> perPage
                else -> Helpers.getClosestInt(perPage, numberOfRestaurantsPerPage)
            }
            getRestaurants()
        }


    init {
        getRestaurants()
        getCities()
    }

    fun curateState(s: String): String {
        if (s.isNullOrBlank()) {
            return ""
        }

        return Helpers.getClosestString(s, states)
    }

    fun setFilters(
        name: String,
        address: String,
        state: String,
        city: String,
        zip: String,
        country: String,
        n: Int
    ): Boolean {
        checkFilters(name, address, state, city, zip, country, n)

        return true
    }

    private fun checkFilters(
        name: String,
        address: String,
        state: String,
        city: String,
        zip: String,
        country: String,
        n: Int
    ): Boolean {
        if (numberOfRestaurantsPerPage.contains(n) && (name.isNotBlank() || address.isNotBlank() || state.isNotBlank() || city.isNotBlank() || zip.isNotBlank() || country.isNotBlank())) {
            return true
        }

        return false
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            val response = repository.getRestaurants(country = country, state = state, city = city, zip = zip, address = address, name = name, page = page, perPage = perPage)
            restaurants.value = response.restaurants
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            val response = repository.getCities()
            cities = response.cities
        }
    }
}