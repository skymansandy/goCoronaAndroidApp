package dev.skymansandy.gocorona.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import dev.skymansandy.base.dsl.launchActivity
import dev.skymansandy.base.ui.base.BaseActivity
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.ActivitySplashBinding
import dev.skymansandy.gocorona.presentation.main.MainActivity

class SplashActivity(override val layoutId: Int = R.layout.activity_splash) :
    BaseActivity<ActivitySplashBinding, Void, Void, SplashViewModel>() {

    companion object {
        const val SPLASH_TIME_MILLIS = 2000L
    }

    override val navControllerId = -1
    override val snackBarView: View
        get() = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.actionLaunchApp().observe(this, Observer {
            launchActivity<MainActivity>()
            finish()
        })
    }

    override fun renderViewState(newState: Void) {

    }
}