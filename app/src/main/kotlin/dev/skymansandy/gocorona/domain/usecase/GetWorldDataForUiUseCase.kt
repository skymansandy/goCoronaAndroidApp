package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.HomeState
import dev.skymansandy.gocorona.presentation.home.StatCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWorldDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Flow<HomeState> {
        return flow homeState@{
            emit(HomeState.Loading)
            delay(1000)
            val countriesStat = goCoronaRepository.getWorldData()
            countriesStat.collect {
                it?.let {
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
                        HomeState.NonIndiaStats(
                            placeName = "Worldwide",
                            lastUpdated = worldData.lastUpdatedUiStr,
                            confirmed = confirmedStat,
                            active = activeStat,
                            recovered = recoveredStat,
                            deceased = deceasedStat
                        )
                    )
                }
            }
        }
    }

}