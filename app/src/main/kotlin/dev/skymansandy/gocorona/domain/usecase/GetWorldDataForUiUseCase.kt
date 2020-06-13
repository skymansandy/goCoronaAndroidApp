package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.world.WorldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorldDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Flow<WorldState> {
        return flow {
            emit(WorldState.Loading)
            val worldDetails = goCoronaRepository.getWorldData()
            val countryStats = goCoronaRepository.getCountries()
            worldDetails.combine(countryStats) { details, stat ->
                details?.let {
                    stat.let { countries ->
                        val countryDataStats = arrayListOf<CovidStat>()
                        for (district in countries) {
                            countryDataStats += CovidStat(
                                code = district.countryCode,
                                name = district.name,
                                confirmed = district.cases,
                                active = district.active,
                                recovered = district.recovered,
                                deceased = district.deaths
                            )
                        }

                        emit(
                            WorldState.WorldStats(
                                lastUpdated = details.lastUpdatedUiStr,
                                active = details.active,
                                confirmed = details.cases,
                                confirmedToday = details.todayCases,
                                recovered = details.recovered,
                                recoveredToday = details.todayRecovered,
                                deaths = details.deaths,
                                deathsToday = details.todayDeaths,
                                stats = countryDataStats
                            )
                        )
                    }
                }
            }.collect()
        }
    }

}