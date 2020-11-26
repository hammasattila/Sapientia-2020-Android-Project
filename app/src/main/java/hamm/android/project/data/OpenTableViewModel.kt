package hamm.android.project.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hamm.android.project.model.Cities
import hamm.android.project.model.Restaurant
import kotlinx.coroutines.launch

class OpenTableViewModel(private val repository: OpenTableRepository): ViewModel() {

    val restaurants: MutableLiveData<ArrayList<Restaurant>> = MutableLiveData()

    fun getRestaurants(state: String){
        viewModelScope.launch {
            val response = repository.getRestaurants(state)
            restaurants.value = response.restaurants
        }
    }
}