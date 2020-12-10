package hamm.android.project.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hamm.android.project.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .baseUrl("https://ratpark-api.imok.space/")
            .addConverterFactory(GsonConverterFactory.create(/*GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()*/))
            .client(okHttp)
            .build()
    }

    val api: OpenTableAPI by lazy {
        retrofit.create(OpenTableAPI::class.java)
    }
}