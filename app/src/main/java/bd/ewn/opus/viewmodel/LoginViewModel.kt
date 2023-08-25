package bd.ewn.opus.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.LoginPojo
import bd.ewn.opus.service.repository.OpusRepoImpl

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginRepo = OpusRepoImpl.getInstance()

    fun loginResponse(
        context:Context,
        username: String,
        password: String
    ): MutableLiveData<LoginPojo> {
        return loginRepo.cLoginResponse(context, username, password)
    }
}
