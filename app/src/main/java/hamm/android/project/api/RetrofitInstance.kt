package hamm.android.project.api

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
//        val okHttp = OkHttpClient.Builder().addInterceptor(interceptor).build()


        val okHttp = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val request: Request = chain.request()
                Log.d("${request.method} ---------------->", request.url.toString())

                val response = try {
                    chain.proceed(request)
                } catch (e: Exception) {
                    Response.Builder()
                        .code(200)
                        .message("Failed to connect.")
                        .body("{}".toResponseBody("application/json; charset=utf-8".toMediaTypeOrNull()!!))
                        .protocol(Protocol.HTTP_1_1)
                        .request(request)
                        .build()
                }

                Log.d("${request.method} <----------------", "${request.url.toString()} {${response.body?.contentLength().toString()}bytes}")

                response
            })
            .build()


        Retrofit.Builder()
            .baseUrl("https://ratpark-api.imok.space/")
//            .baseUrl("http://192.168.0.16:6969/")
            .addConverterFactory(GsonConverterFactory.create(/*GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()*/))
            .client(okHttp)
            .build()
    }

    val api: OpenTableAPI by lazy {
        retrofit.create(OpenTableAPI::class.java)
    }
}