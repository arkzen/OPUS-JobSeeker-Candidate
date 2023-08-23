package bd.ewn.opus.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.PassResetEmailPojo
import bd.ewn.opus.service.repository.OpusRepoImpl

class ResetPasswordViewmodel(application: Application) : AndroidViewModel(application) {

    private val resetPassRepo = OpusRepoImpl.getInstance()

    fun resetPassResponse(
        context: Context,
       pass: String
    ): MutableLiveData<PassResetEmailPojo> {
        return resetPassRepo.resetPass(context,pass)
    }
}