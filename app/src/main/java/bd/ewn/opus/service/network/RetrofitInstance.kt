package bd.ewn.opus.service.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "https://opus.farjanul.com"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            try {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return retrofit
    }
}
