package dev.skymansandy.gocorona.presentation.main

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<MainState, Void>() {

    fun refreshStats() {
        viewModelScope.launch {
            fetchCovid19StatsUseCase().collect { viewState = it }
        }
    }
}