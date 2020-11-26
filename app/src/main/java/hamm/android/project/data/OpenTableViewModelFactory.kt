package hamm.android.project.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OpenTableViewModelFactory(private val repository: OpenTableRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(OpenTableRepository::class.java).newInstance(repository)
    }

}