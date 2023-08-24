package bd.ewn.opus.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.SignUpPojo
import bd.ewn.opus.service.network.request.SignupBody
import bd.ewn.opus.service.repository.OpusRepoImpl

class SignUpViewmodel(application: Application) : AndroidViewModel(application) {
    private val signUpRepo = OpusRepoImpl.getInstance()

    fun signUpResponse(
        context: Context,
        signupbody: SignupBody
    ): MutableLiveData<SignUpPojo> {
        return signUpRepo.postSignUp(context, signupbody)
    }
}