package hamm.android.project.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hamm.android.project.data.OpenTableRepository

class OpenTableViewModelFactory(private val repository: OpenTableRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OpenTableRepository::class.java).newInstance(repository)
    }

}