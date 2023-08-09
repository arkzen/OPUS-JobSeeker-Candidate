package bd.ewn.opus.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import bd.ewn.opus.R
import bd.ewn.opus.view.ui.activity.Authentication
import bd.ewn.opus.view.ui.activity.Homepage

var state:Boolean?=false
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        Handler(Looper.getMainLooper()).postDelayed({
            if (state == true) {
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, Authentication::class.java)
                startActivity(intent)
            }
            finish()
        }, 2000) // 2000 milliseconds = 2 seconds delay
    }
    }
