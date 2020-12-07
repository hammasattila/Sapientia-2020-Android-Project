package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import hamm.android.project.data.RestaurantRepository

class RestaurantFilterFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val countries
        get() = RestaurantRepository.countries
    val perPage
        get() = RestaurantRepository.numberOfRestaurantsPerPage
}