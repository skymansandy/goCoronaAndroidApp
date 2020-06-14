package dev.skymansandy.gocorona.presentation.main

import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<Void, Void>() {

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }
}