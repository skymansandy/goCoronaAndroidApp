package dev.skymansandy.gocorona.presentation.home

import androidx.lifecycle.LiveData
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetCountryDataUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getCountryDataUseCase: GetCountryDataUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<HomeState, HomeEvent>() {

    private var countryCode: String = AppConstant.IN
    private lateinit var liveData: LiveData<HomeState>

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
        if (::liveData.isInitialized)
            mediator.removeSource(liveData)
        liveData = getCountryDataUseCase(countryCode)
        mediator.addSource(liveData) {
            viewState = it
        }
    }
}