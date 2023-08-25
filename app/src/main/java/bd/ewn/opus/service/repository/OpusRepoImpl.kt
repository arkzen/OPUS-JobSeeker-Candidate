package bd.ewn.opus.service.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.CurrentLocationModel
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.model.SignUpPojo
import bd.ewn.opus.service.network.ApiServices
import bd.ewn.opus.service.network.RetrofitInstance
import bd.ewn.opus.service.network.request.LoginBody
import bd.ewn.opus.service.network.request.ResetPassBody
import bd.ewn.opus.service.network.request.SignupBody
import bd.ewn.opus.service.network.request.resetReqBody
import bd.ewn.opus.service.util.SharedPrefDataProvider
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.Locale

class OpusRepoImpl private constructor() : OpusRepository {

    private val mLivedata: MutableLiveData<LoginPojo> = MutableLiveData()
    private val signUpLivedata: MutableLiveData<SignUpPojo> = MutableLiveData()
    private var getOtp: MutableLiveData<PassResetEmailPojo> = MutableLiveData()
    private var setPass: MutableLiveData<PassResetEmailPojo> = MutableLiveData()
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

        val apiServices = rInstance.getRetrofitInstance()?.create(ApiServices::class.java)
        val emailbody=resetReqBody(email)
        val call = apiServices?.getOTPbyEmail(emailbody)

        try {
            call?.enqueue(object : Callback<PassResetEmailPojo> {
                override fun onResponse(
                    call: Call<PassResetEmailPojo>,
                    response: Response<PassResetEmailPojo>
                ) {

                    if (!response.isSuccessful) {
                        Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show()
                        return
                    }
                    val successMsg = response.body()
                    SharedPrefDataProvider.initialize(context)
                    SharedPrefDataProvider.setResetPassEmail(email)
                    getOtp.postValue(successMsg!!)
                }

                override fun onFailure(call: Call<PassResetEmailPojo>, t: Throwable) {
                    Toast.makeText(context, "OTP send failed", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "OTP send failed", Toast.LENGTH_SHORT).show()
        }

   return getOtp }

    override fun resetPass(context: Context,pass:String): MutableLiveData<PassResetEmailPojo> {

        val apiServices = rInstance.getRetrofitInstance()?.create(ApiServices::class.java)
        val username:String?=SharedPrefDataProvider.getResetPassEmail()
        val otp=SharedPrefDataProvider.getResetPassOtp()
        val body= username?.let { ResetPassBody(it,pass,otp) }
        val call = body?.let { apiServices?.resetPass(it) }


        try {
            call?.enqueue(object : Callback<PassResetEmailPojo> {
                override fun onResponse(
                    call: Call<PassResetEmailPojo>,
                    response: Response<PassResetEmailPojo>
                ) {

                    if (!response.isSuccessful) {
                        Toast.makeText(context, "Set Password Failed", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val successMsg = response.body()
                    setPass.postValue(successMsg!!)
                }

                override fun onFailure(call: Call<PassResetEmailPojo>, t: Throwable) {
                    Toast.makeText(context, "Set Password Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Set Password Failed", Toast.LENGTH_SHORT).show()
        }

        return setPass
    }

    override fun getCurrentLocation(context: Context): MutableLiveData<CurrentLocationModel> {
        val currentLocationLiveData = MutableLiveData<CurrentLocationModel>()

        val client = LocationServices.getFusedLocationProviderClient(context)

        val permissionCheck = PackageManager.PERMISSION_GRANTED
        val fineLocationPermission = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (fineLocationPermission != permissionCheck || coarseLocationPermission != permissionCheck) {
            return currentLocationLiveData
        }

        val task = client.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val latitude = location.latitude
                val longitude = location.longitude

                try {
                    val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                    if (addresses != null && addresses.size > 0) {
                        val address = addresses[0]
                        val locationName = address.getAddressLine(0)

                        val locationModel = CurrentLocationModel(
                            latitude,
                            longitude,
                            locationName
                        )
                        currentLocationLiveData.value = locationModel
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return currentLocationLiveData
    }

    override fun postSignUp(context: Context,signupBody: SignupBody): MutableLiveData<SignUpPojo> {
        val apiServices = rInstance.getRetrofitInstance()?.create(ApiServices::class.java)

        val call = apiServices?.postSignUp(signupBody)
        try {
            call?.enqueue(object : Callback<SignUpPojo> {
                override fun onResponse(
                    call: Call<SignUpPojo>,
                    response: Response<SignUpPojo>
                ) {

                    if (!response.isSuccessful) {
                        Toast.makeText(context, "SignUp Failed", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val SignUpResponse = response.body()

                    signUpLivedata.postValue(SignUpResponse!!)
                }

                override fun onFailure(call: Call<SignUpPojo>, t: Throwable) {
                    Toast.makeText(context, "SignUp onFailure", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "SignUp Exception", Toast.LENGTH_SHORT).show()
        }
        return signUpLivedata

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
