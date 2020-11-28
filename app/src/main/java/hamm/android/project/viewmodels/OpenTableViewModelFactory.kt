package hamm.android.project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hamm.android.project.data.RestaurantRepository

class OpenTableViewModelFactory(private val repository: RestaurantRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RestaurantRepository::class.java).newInstance(repository)
    }

}