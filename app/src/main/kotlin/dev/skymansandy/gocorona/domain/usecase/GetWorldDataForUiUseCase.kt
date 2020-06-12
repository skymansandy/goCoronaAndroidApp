package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.world.WorldState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorldDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Flow<WorldState> {
        return flow homeState@{
            emit(WorldState.Loading)
            delay(1000)
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

                        it.let {
                            emit(
                                WorldState.WorldStats(
                                    lastUpdated = it.lastUpdatedUiStr,
                                    active = it.active,
                                    confirmed = it.cases,
                                    confirmedToday = it.todayCases,
                                    recovered = it.recovered,
                                    recoveredToday = it.todayRecovered,
                                    deaths = it.deaths,
                                    deathsToday = it.todayDeaths,
                                    stats = countryDataStats
                                )
                            )
                        }
                    }
                }
            }.collect()
        }
    }

}