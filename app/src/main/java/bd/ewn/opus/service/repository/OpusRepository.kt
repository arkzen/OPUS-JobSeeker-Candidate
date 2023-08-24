package bd.ewn.opus.service.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.CurrentLocationModel
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.model.SignUpPojo
import bd.ewn.opus.service.network.request.SignupBody

interface OpusRepository {
    fun postSignUp(context: Context,signupBody:SignupBody):MutableLiveData<SignUpPojo>
    fun cLoginResponse(context: Context, username:String, password:String): MutableLiveData<LoginPojo>
    fun getCurrentLocation(context: Context):MutableLiveData<CurrentLocationModel>
    fun getOTPbyEmail(context: Context,email:String):MutableLiveData<PassResetEmailPojo>
    fun resetPass(context: Context,pass:String):MutableLiveData<PassResetEmailPojo>


}