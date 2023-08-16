package bd.ewn.opus.service.network

import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.network.request.LoginBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {
    @POST("/user/login/")
    fun getUser(@Header("client-id") clientId: String,
                @Header("client-secret") clientSecret: String,
                @Header("grant-type") grantType: String,
                @Body requestBody: LoginBody) : retrofit2.Call<LoginPojo>
}