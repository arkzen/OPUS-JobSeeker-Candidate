package bd.ewn.opus.service.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.LoginPojo

interface OpusRepository {
    fun cLoginResponse(context: Context, username:String, password:String): MutableLiveData<LoginPojo>
}