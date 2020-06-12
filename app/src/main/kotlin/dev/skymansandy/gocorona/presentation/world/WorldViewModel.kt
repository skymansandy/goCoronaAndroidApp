package dev.skymansandy.gocorona.presentation.world

import androidx.lifecycle.LiveData
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.domain.usecase.FetchCovid19StatsUseCase
import dev.skymansandy.gocorona.domain.usecase.GetCountryDataUseCase
import dev.skymansandy.gocorona.presentation.home.HomeState
import javax.inject.Inject

class WorldViewModel @Inject constructor(
    private val getCountryDataUseCase: GetCountryDataUseCase,
    private val fetchCovid19StatsUseCase: FetchCovid19StatsUseCase
) : BaseViewModel<WorldState, WorldEvent>() {

    private var countryCode: String = AppConstant.IN
    private lateinit var liveData: LiveData<HomeState>

    init {
        getCountryData(countryCode)
    }

    fun refreshStats() {
        fetchCovid19StatsUseCase()
    }

    override fun onUserEvent(viewEvent: WorldEvent) {
        super.onUserEvent(viewEvent)
        when (viewEvent) {
            is WorldEvent.CountryClicked -> {
                countryCode = viewEvent.countryId
                getCountryData(countryCode)
            }
        }
    }

    private fun getCountryData(countryCode: String) {
        if (::liveData.isInitialized)
            mediator.removeSource(liveData)
        liveData = getCountryDataUseCase()
        mediator.addSource(liveData) {
//            viewState = it
        }
    }
}