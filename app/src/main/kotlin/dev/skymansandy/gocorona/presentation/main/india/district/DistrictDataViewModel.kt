package dev.skymansandy.gocorona.presentation.main.india.district

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetDistrictDataForUiUseCase
import dev.skymansandy.gocorona.domain.usecase.api.FetchCovid19StatsUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DistrictDataViewModel @Inject constructor(
    private val getDistrictDataForUiUseCase: GetDistrictDataForUiUseCase
) : BaseViewModel<DistrictDataState, DistrictDataEvent>() {

    fun getDistrictData(code: String) {
        viewModelScope.launch {
            getDistrictDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }
}