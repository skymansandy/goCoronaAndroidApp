package dev.skymansandy.gocorona.presentation.home

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetCountryDataUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCountryDataUseCase: GetCountryDataUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<HomeState, HomeEvent>() {

    var countryCode: String = "IN"

    init {
        getCountryData(countryCode)
    }

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }

    override fun onUserEvent(viewEvent: HomeEvent) {
        super.onUserEvent(viewEvent)
        when (viewEvent) {
            is HomeEvent.CountryClicked -> {
                countryCode = viewEvent.countryId
                getCountryData(countryCode)
            }
        }
    }

    private fun getCountryData(countryCode: String) {
        viewModelScope.launch {
            getCountryDataUseCase(countryCode).collect {
                viewState = it
            }
        }
    }
}