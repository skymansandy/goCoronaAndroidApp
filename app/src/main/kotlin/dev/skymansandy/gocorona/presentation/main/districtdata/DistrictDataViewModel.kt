package dev.skymansandy.gocorona.presentation.main.districtdata

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetDistrictDataForUiUseCase
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DistrictDataViewModel @Inject constructor(
    private val getDistrictDataForUiUseCase: GetDistrictDataForUiUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<DistrictDataState, DistrictDataEvent>() {

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }

    fun getDistrictData(code: String) {
        viewModelScope.launch {
            getDistrictDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }
}