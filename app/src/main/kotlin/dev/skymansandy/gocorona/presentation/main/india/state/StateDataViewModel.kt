package dev.skymansandy.gocorona.presentation.main.india.state

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetStateDataForUiUseCase
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class StateDataViewModel @Inject constructor(
    private val getStateDataForUiUseCase: GetStateDataForUiUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<StateDataState, StateDataEvent>() {

    fun getStateData(code: String) {
        viewModelScope.launch {
            getStateDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }
}