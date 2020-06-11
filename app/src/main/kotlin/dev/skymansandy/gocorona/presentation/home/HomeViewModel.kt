package dev.skymansandy.gocorona.presentation.home

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetIndiaDataForUiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetIndiaDistrictDataFromApiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetIndiaStatesDataFromApiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetWorldDataFromApiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase,
    private val getWorldDataFromApiUseCase: GetWorldDataFromApiUseCase,
    private val getIndiaStatesDataFromApiUseCase: GetIndiaStatesDataFromApiUseCase,
    private val getIndiaDistrictDataFromApiUseCase: GetIndiaDistrictDataFromApiUseCase
) : BaseViewModel<HomeState, HomeEvent>() {

    init {
        viewModelScope.launch {
            getIndiaDataForUiUseCase().collect {
                viewState = it
            }
        }
    }

    fun refreshStats() {
        getWorldDataFromApiUseCase()
        getIndiaStatesDataFromApiUseCase()
        getIndiaDistrictDataFromApiUseCase()
    }

}