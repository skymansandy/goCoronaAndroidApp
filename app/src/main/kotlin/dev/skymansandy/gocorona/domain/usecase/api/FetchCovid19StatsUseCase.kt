package dev.skymansandy.gocorona.domain.usecase.api

import javax.inject.Inject

class FetchCovid19StatsUseCase @Inject constructor(
    private val fetchWorldDataUseCase: FetchWorldDataUseCase,
    private val fetchCountryDataUseCase: FetchCountryDataUseCase,
    private val fetchStatesDataUseCase: FetchStatesDataUseCase,
    private val fetchDistrictsDataUseCase: FetchDistrictsDataUseCase
) {

    operator fun invoke() {
        fetchWorldDataUseCase()
        fetchCountryDataUseCase()
        fetchStatesDataUseCase()
        fetchDistrictsDataUseCase()
    }

}