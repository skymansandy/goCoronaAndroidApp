package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.presentation.main.MainState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class FetchCovid19StatsUseCase @Inject constructor(
    private val saveWorldDataUseCase: SaveWorldDataUseCase,
    private val saveCountryDataUseCase: SaveCountryDataUseCase,
    private val saveStatesDataUseCase: SaveStatesDataUseCase,
    private val saveDistrictsDataUseCase: SaveDistrictsDataUseCase
) {

    operator fun invoke(): Flow<MainState> {
        return flow {
            emit(MainState.Loading)
            try {
                Timber.tag("Tag").d("Made World API call")
                val worldResponse = saveWorldDataUseCase().await()
                saveWorldDataUseCase.save(worldResponse)

                Timber.tag("Tag").d("Made Countries API call")
                val countriesResponse = saveCountryDataUseCase().await()
                saveCountryDataUseCase.save(countriesResponse)

                Timber.tag("Tag").d("Made States API call")
                val statesResponse = saveStatesDataUseCase().await()
                saveStatesDataUseCase.save(statesResponse)

                Timber.tag("Tag").d("Made District API call")
                val districtsResponse = saveDistrictsDataUseCase().await()
                saveDistrictsDataUseCase.save(districtsResponse)

                emit(MainState.Loaded)
            } catch (e: Exception) {
                emit(MainState.Error(e.message))
            }
        }
    }
}