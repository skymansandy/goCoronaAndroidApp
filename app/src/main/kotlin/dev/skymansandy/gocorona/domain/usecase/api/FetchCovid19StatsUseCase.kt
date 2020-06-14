package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.presentation.main.MainState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCovid19StatsUseCase @Inject constructor(
    private val fetchWorldDataUseCase: FetchWorldDataUseCase,
    private val fetchCountryDataUseCase: FetchCountryDataUseCase,
    private val fetchStatesDataUseCase: FetchStatesDataUseCase,
    private val fetchDistrictsDataUseCase: FetchDistrictsDataUseCase
) {

    operator fun invoke(): Flow<MainState> {
        return flow {
            emit(MainState.Loading)
            fetchWorldDataUseCase()
            fetchCountryDataUseCase()
            fetchStatesDataUseCase()
            fetchDistrictsDataUseCase()
            delay(1000)
            emit(MainState.Loaded)
        }
    }
}