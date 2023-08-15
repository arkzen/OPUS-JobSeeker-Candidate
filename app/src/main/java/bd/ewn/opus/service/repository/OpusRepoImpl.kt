package bd.ewn.opus.service.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.network.ApiServices
import bd.ewn.opus.service.network.RetrofitInstance
import bd.ewn.opus.service.network.request.LoginBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpusRepoImpl private constructor() : OpusRepository {

    private val mLivedata: MutableLiveData<LoginPojo> = MutableLiveData()
    private  var rInstance:RetrofitInstance = RetrofitInstance()

    override fun cLoginResponse(
        context: Context,
        username: String,
        password: String
    ): MutableLiveData<LoginPojo> {

        val apiServices=rInstance.getRetrofitInstance()?.create(ApiServices::class.java)
        val requestBody = LoginBody(username, password)
        val call = apiServices?.getUser(requestBody)

        try {
            call?.enqueue(object : Callback<LoginPojo> {
                override fun onResponse(
                    call: Call<LoginPojo>,
                    response: Response<LoginPojo>
                ) {
                    if (!response.isSuccessful) {
                        val errorMessage = response.message()
                        Log.d("EWN2745", errorMessage)
                        Toast.makeText(context, "Login Failed: $errorMessage", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val loginResponse = response.body()

                    Log.d("EWN2745", loginResponse!!.access_token)
                    mLivedata.postValue(loginResponse!!)
                }

                override fun onFailure(call: Call<LoginPojo>, t: Throwable) {
                    Toast.makeText(context, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "An Error Occurred, Try Later!!", Toast.LENGTH_SHORT).show()
        }
        return mLivedata
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
