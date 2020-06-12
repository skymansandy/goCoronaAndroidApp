package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.StatCard
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
                                confirmed = district.cases.toInt(),
                                active = district.active.toInt(),
                                recovered = district.recovered.toInt(),
                                deceased = district.deaths.toInt()
                            )
                        }

                        it.let {
                            val worldData = it

                            val confirmedStat =
                                StatCard(
                                    worldData.cases, worldData.todayCases,
                                    arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                                )
                            val activeStat =
                                StatCard(
                                    worldData.active, "0",
                                    arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                                )
                            val recoveredStat =
                                StatCard(
                                    worldData.recovered, worldData.todayRecovered,
                                    arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                                )
                            val deceasedStat =
                                StatCard(
                                    worldData.deaths, worldData.todayDeaths,
                                    arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                                )

                            emit(
                                WorldState.WorldStats(
                                    lastUpdated = worldData.lastUpdatedUiStr,
                                    confirmed = confirmedStat,
                                    active = activeStat,
                                    recovered = recoveredStat,
                                    deceased = deceasedStat,
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