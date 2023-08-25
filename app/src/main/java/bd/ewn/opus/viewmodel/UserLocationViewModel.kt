package bd.ewn.opus.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import bd.ewn.opus.service.model.CurrentLocationModel
import bd.ewn.opus.service.repository.OpusRepoImpl

class UserLocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationRepo = OpusRepoImpl.getInstance()

    fun getCurrentLocation(context: Context): MutableLiveData<CurrentLocationModel> {
        return locationRepo.getCurrentLocation(context)
    }
}