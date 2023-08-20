package bd.ewn.opus.service.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.model.PassResetEmailPojo

interface OpusRepository {
    fun cLoginResponse(context: Context, username:String, password:String): MutableLiveData<LoginPojo>
    fun getOTPbyEmail(context: Context,email:String):MutableLiveData<PassResetEmailPojo>
}