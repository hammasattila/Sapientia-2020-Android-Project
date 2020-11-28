package hamm.android.project.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hamm.android.project.data.OpenTableRepository
import hamm.android.project.model.Restaurant
import hamm.android.project.utils.Constants
import kotlinx.coroutines.launch

class OpenTableViewModel(private val repository: OpenTableRepository) : ViewModel() {

    val restaurants: MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()
    var state: String = Constants.states.iterator().next().key
        set(state) {
            synchronized(this) {
                field = when (Constants.states.containsKey(state)) {
                    true -> state
                    else -> field
                }
                getRestaurants()
            }
        }

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            val response = repository.getRestaurants(state)
            restaurants.value = response.restaurants
        }
    }
}