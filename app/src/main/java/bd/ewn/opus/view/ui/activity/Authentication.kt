package bd.ewn.opus.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import bd.ewn.opus.R
import bd.ewn.opus.service.repository.OpusRepoImpl

class Authentication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_auth)


        val username = "farjanul11@gmail.com"
        val password = "helloadmin"

        val repository = OpusRepoImpl.getInstance()

        repository.cLoginResponse(this@Authentication, username, password)
    }
    }
