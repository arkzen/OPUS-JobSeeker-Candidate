package bd.ewn.opus.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.repository.OpusRepoImpl

class PassResReqViewmodel(application: Application) : AndroidViewModel(application) {

    private val otpSendRepo = OpusRepoImpl.getInstance()

    fun OtpSendResponse(
        context: Context,
        email: String
    ): MutableLiveData<PassResetEmailPojo> {
        return otpSendRepo.getOTPbyEmail(context,email)
    }
}