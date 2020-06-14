package dev.skymansandy.gocorona.presentation.splash

import android.os.Handler
import dev.skymansandy.base.lifecycle.event.SingleLiveEvent
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<Void, Void>() {

    init {
        fetchCovid19StatsUseCase()
        Handler().postDelayed(
            { _actionLaunchApp.call() },
            SplashActivity.SPLASH_TIME_MILLIS
        )
    }

    private val _actionLaunchApp = SingleLiveEvent<Boolean>()
    fun actionLaunchApp() = _actionLaunchApp
}