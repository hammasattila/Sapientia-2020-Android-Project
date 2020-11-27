package hamm.android.project.api

import hamm.android.project.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://opentable.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: OpenTableAPI by lazy {
        retrofit.create(OpenTableAPI::class.java)
    }
}