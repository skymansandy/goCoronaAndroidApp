package dev.skymansandy.gocorona.presentation.main.world

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetWorldDataForUiUseCase
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorldViewModel @Inject constructor(
    private val getWorldDataForUiUseCase: GetWorldDataForUiUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<WorldState, WorldEvent>() {

    init {
        viewModelScope.launch {
            getWorldDataForUiUseCase().collect {
                viewState = it
            }
        }
    }

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }
}