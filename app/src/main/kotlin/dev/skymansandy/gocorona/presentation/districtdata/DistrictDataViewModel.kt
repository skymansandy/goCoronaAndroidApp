package dev.skymansandy.gocorona.presentation.districtdata

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetDistrictDataForUiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DistrictDataViewModel @Inject constructor(
    val getDistrictDataForUiUseCase: GetDistrictDataForUiUseCase,
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