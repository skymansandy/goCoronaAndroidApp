package dev.skymansandy.gocorona.presentation.districtdata

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetDistrictDataForUiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DistrictDataViewModel @Inject constructor(
    val getDistrictDataForUiUseCase: GetDistrictDataForUiUseCase
) : BaseViewModel<DistrictDataState, DistrictDataEvent>() {

    fun refreshStats() {

    }

    fun getDistrictData(code: String) {
        viewModelScope.launch {
            getDistrictDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }

}