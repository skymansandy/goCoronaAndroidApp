package dev.skymansandy.gocorona.presentation.main.world.country

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetOtherCountryDataForUiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDataViewModel @Inject constructor(
    private val getOtherCountryDataForUiUseCase: GetOtherCountryDataForUiUseCase
) : BaseViewModel<CountryDataState, CountryDataEvent>() {

    fun getDistrictData(code: String) {
        viewModelScope.launch {
            getOtherCountryDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }
}