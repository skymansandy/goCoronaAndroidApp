package dev.skymansandy.gocorona.presentation.home

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetIndiaDataForUiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<HomeState, HomeEvent>() {

    init {
        viewModelScope.launch {
            getIndiaDataForUiUseCase().collect {
                viewState = it
            }
        }
    }

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }

}