package hamm.android.project.model

import com.google.gson.annotations.SerializedName

data class Restaurants (
    val count: Int,
    @SerializedName("per_page")
    val pageSize: Int,
    val restaurants: ArrayList<Restaurant>
        )