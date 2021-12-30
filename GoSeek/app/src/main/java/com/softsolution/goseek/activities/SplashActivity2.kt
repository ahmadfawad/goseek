package com.softsolution.goseek.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.softsolution.goseek.R
import com.softsolution.goseek.network.LocalPreference

class SplashActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        FullScreencall()

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            if (LocalPreference.shared.user != null) {
                if (LocalPreference.shared.user?.status == 2) {
                    startActivity(Intent(this, EmployerDashboard::class.java))
                } else {
                    startActivity(Intent(this, UserDashboard::class.java))
                }
            } else {
                startActivity(Intent(this, UserDashboard::class.java))
            }


            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

    fun FullScreencall() {
        //for new api versions.
        val decorView = window.decorView
        val uiOptions =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        decorView.systemUiVisibility = uiOptions
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }
}