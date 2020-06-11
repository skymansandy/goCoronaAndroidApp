package dev.skymansandy.gocorona.presentation.splash

import android.os.Handler
import dev.skymansandy.base.lifecycle.event.SingleLiveEvent
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetIndiaStatesDataFromApiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetWorldDataFromApiUseCase
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val getWorldDataFromApiUseCase: GetWorldDataFromApiUseCase,
    private val getIndiaStatesDataFromApiUseCase: GetIndiaStatesDataFromApiUseCase
) : BaseViewModel<Void, Void>() {

    init {
        getWorldDataFromApiUseCase()
        getIndiaStatesDataFromApiUseCase()
        Handler().postDelayed(
            { _actionLaunchApp.call() },
            SplashActivity.SPLASH_TIME_MILLIS
        )
    }

    private val _actionLaunchApp = SingleLiveEvent<Boolean>()
    fun actionLaunchApp() = _actionLaunchApp
}