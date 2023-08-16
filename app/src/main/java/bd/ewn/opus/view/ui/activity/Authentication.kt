package bd.ewn.opus.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import bd.ewn.opus.R
import bd.ewn.opus.service.repository.OpusRepoImpl
import bd.ewn.opus.service.repository.OpusRepository


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_auth)

        val username = "farjanul11@gmail.com"
        val password = "helloadmin"

        val repository=OpusRepoImpl.getInstance()

        repository.cLoginResponse(this@MainActivity,username,password)

    }
}
