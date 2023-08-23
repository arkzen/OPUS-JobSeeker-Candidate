package bd.ewn.opus.service.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "https://opus.farjanul.com"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            val interpeter = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }
            val client = OkHttpClient.Builder().apply {
                this.addInterceptor(interpeter)
            }.build()
            try {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return retrofit
    }
}
