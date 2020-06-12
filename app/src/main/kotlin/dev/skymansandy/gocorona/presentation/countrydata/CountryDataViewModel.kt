package dev.skymansandy.gocorona.presentation.countrydata

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetOtherCountryDataForUiUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryDataViewModel @Inject constructor(
    private val getOtherCountryDataForUiUseCase: GetOtherCountryDataForUiUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<CountryDataState, CountryDataEvent>() {

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }

    fun getDistrictData(code: String) {
        viewModelScope.launch {
            getOtherCountryDataForUiUseCase(code).collect {
                viewState = it
            }
        }
    }
}