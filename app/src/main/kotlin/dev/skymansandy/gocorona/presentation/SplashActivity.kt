package dev.skymansandy.gocorona.presentation

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dev.skymansandy.base.dsl.launchActivity
import dev.skymansandy.gocorona.R

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME_MILLIS = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            launchActivity<MainActivity>()
            finish()
        },
            SPLASH_TIME_MILLIS
        )
    }
}