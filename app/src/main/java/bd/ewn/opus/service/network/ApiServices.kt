package bd.ewn.opus.service.network

import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.model.SignUpPojo
import bd.ewn.opus.service.network.request.LoginBody
import bd.ewn.opus.service.network.request.ResetPassBody
import bd.ewn.opus.service.network.request.SignupBody
import bd.ewn.opus.service.network.request.resetReqBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiServices {

    @POST("/user/signup/")
    fun postSignUp(@Body signupBody:SignupBody):Call<SignUpPojo>

    @POST("/user/login/")
    fun getUser(
        @Header("client-id") clientId: String,
        @Header("client-secret") clientSecret: String,
        @Header("grant-type") grantType: String,
        @Body requestBody: LoginBody
    ): Call<LoginPojo>

    @POST("/user/password_reset_request/")
    fun getOTPbyEmail(@Body emailbody: resetReqBody): Call<PassResetEmailPojo>

    @POST("/user/password_reset/")
    fun resetPass(@Body requestBody:ResetPassBody):Call<PassResetEmailPojo>
}