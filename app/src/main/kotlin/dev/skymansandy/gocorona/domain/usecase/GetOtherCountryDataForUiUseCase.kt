package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.main.countrydata.CountryDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOtherCountryDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(countryCode: String): Flow<CountryDataState> {
        return flow {
            emit(CountryDataState.Loading)
            val countriesStat = goCoronaRepository.getCountryData(countryCode)
            countriesStat.collect {
                it?.let {
                    emit(
                        CountryDataState.CountryStats(
                            placeName = it.name,
                            lastUpdated = it.lastUpdatedUiStr,
                            active = it.active,
                            confirmed = it.cases,
                            confirmedToday = it.casesToday,
                            recovered = it.recovered,
                            recoveredToday = it.recoveredToday,
                            deaths = it.deaths,
                            deathsToday = it.deathsToday
                        )
                    )
                }
            }
        }
    }

}