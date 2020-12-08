package hamm.android.project.data

import androidx.lifecycle.LiveData
import hamm.android.project.api.RetrofitInstance
import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurant
import hamm.android.project.model.RestaurantExtension
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    fun getAllRestaurantsAsync(): LiveData<List<Restaurant>> {
        return restaurantDao.getAllRestaurants()
    }

    suspend fun updateRestaurantSync(restaurant: Restaurant) {
        restaurant.ext?.let {
            updateRestaurantExtSync(it)
        }
    }

    suspend fun updateRestaurantExtSync(ext: RestaurantExtension) {
        when (ext.userData.id) {
            0L -> {
                val extId: Long = restaurantDao.insertRestaurantUserData(ext.userData)
                for(i in 0 until ext.images.size) {
                    ext.images[i].extensionId = extId
                }
            }
            else -> restaurantDao.updateRestaurantUserData(ext.userData)
        }

        for(it in ext.images) {
            if (0L == it.id) {
                restaurantDao.insertRestaurantImage(it)
            }
        }
    }

    //    TODO("Replace 'US' with something")
    /**
     * Inserts the
     * @param "The filters"
     * @return Returns the number of filtered restaurants found by api.
     */
    suspend fun getRestaurantsSync(
        country: String? = "US",
        state: String? = null,
        city: String? = null,
        zip: String? = null,
        address: String? = null,
        name: String? = null,
        perPage: Int? = null,
        page: Int? = null
    ): Int {
        val restaurants = RetrofitInstance.api.getRestaurants(country, state, city, zip, address, name, perPage, page)
        restaurantDao.insertAllRestaurants(restaurants.restaurants)
        return restaurants.count
    }

    fun getRestaurantByIdAsync(id: Int): LiveData<Restaurant> {
        return restaurantDao.getRestaurantById(id)
    }

    fun getFavoritesAsync(): LiveData<List<Restaurant>> {
        return restaurantDao.getFavorites()
    }


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
        private var mCities: List<String> = listOf()
        val cities get() = mCities

        init {
//            TODO(Should be a better solution...)
            GlobalScope.launch {
                mCities = getCitiesSync().cities
            }
        }


        private suspend fun getCitiesSync(): Cities {
            return RetrofitInstance.api.getCities()
        }

        fun getStateCode(state: String?): String? {
            if (state.isNullOrBlank()) {
                return null
            }
//            TODO
            try {
                return mapOfStates.filterValues { it == state }.keys.elementAt(0)
            } catch (e: IndexOutOfBoundsException) {
                return null
            }
        }
    }

}