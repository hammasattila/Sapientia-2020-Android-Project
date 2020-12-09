package hamm.android.project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import hamm.android.project.model.RestaurantPhoto

class RestaurantImagesFragmentViewModel(application: Application): AndroidViewModel(application) {
    lateinit var images: List<RestaurantPhoto>
}