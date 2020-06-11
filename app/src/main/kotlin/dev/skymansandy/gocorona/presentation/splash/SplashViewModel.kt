package dev.skymansandy.gocorona.presentation.splash

import android.os.Handler
import dev.skymansandy.base.lifecycle.event.SingleLiveEvent
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor() : BaseViewModel<Void, Void>() {

    init {
        Handler().postDelayed(
            { _actionLaunchApp.call() },
            SplashActivity.SPLASH_TIME_MILLIS
        )
    }

    private val _actionLaunchApp = SingleLiveEvent<Boolean>()
    fun actionLaunchApp() = _actionLaunchApp
}