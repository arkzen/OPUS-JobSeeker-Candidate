package bd.ewn.opus.service.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.network.ApiServices
import bd.ewn.opus.service.network.RetrofitInstance
import bd.ewn.opus.service.network.request.LoginBody
import bd.ewn.opus.service.util.SharedPrefDataProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpusRepoImpl private constructor() : OpusRepository {

    private val mLivedata: MutableLiveData<LoginPojo> = MutableLiveData()
    private var rInstance: RetrofitInstance = RetrofitInstance()

    override fun cLoginResponse(
        context: Context,
        username: String,
        password: String
    ): MutableLiveData<LoginPojo> {

        val apiServices = rInstance.getRetrofitInstance()?.create(ApiServices::class.java)
        val requestBody = LoginBody(username, password)

        val clientId = "Wqp1QNVZQdpWuM3xGiegciX8HRgbRCgT7WujzJV7"
        val clientSecret =
            "OVTBUwzNChgvmtfRjFNlM29WZboeRXqQfYTLoLy4dQETSpsz7yB9WOW0S01dwXuuwkatVDbJHMKK03RnyjCjjjbU64xBnc0U3oTS8LU1kfHp7kRgFxawTY5YC1mj4UA3"
        val grantType = "password"
        val call = apiServices?.getUser(clientId, clientSecret, grantType, requestBody)

        try {
            call?.enqueue(object : Callback<LoginPojo> {
                override fun onResponse(
                    call: Call<LoginPojo>,
                    response: Response<LoginPojo>
                ) {
                    if (!response.isSuccessful) {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val loginResponse = response.body()
                    val accesstoken: String = loginResponse!!.access_token
                    if (accesstoken.isNotEmpty()) {
                        SharedPrefDataProvider.initialize(context)
                        SharedPrefDataProvider.setLoginState(true)
                        SharedPrefDataProvider.setAccessToken(accesstoken)
                    }


                    mLivedata.postValue(loginResponse!!)
                }

                override fun onFailure(call: Call<LoginPojo>, t: Throwable) {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
        }
        return mLivedata
    }

    override fun getOTPbyEmail(
        context: Context,
        email: String
    ): MutableLiveData<PassResetEmailPojo> {
        TODO("Not yet implemented")
    }

    companion object {
        private var instance: OpusRepoImpl? = null

        fun getInstance(): OpusRepoImpl {
            return instance ?: synchronized(this) {
                instance ?: OpusRepoImpl().also { instance = it }
            }
        }
    }


}
