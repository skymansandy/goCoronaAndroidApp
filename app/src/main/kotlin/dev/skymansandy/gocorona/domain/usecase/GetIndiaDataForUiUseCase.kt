package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.StateData
import dev.skymansandy.gocorona.presentation.home.HomeState
import dev.skymansandy.gocorona.presentation.home.StatCard
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetIndiaDataForUiUseCase @Inject constructor(
    private val stateDataDao: StateDataDao
) {

    operator fun invoke(): Flow<HomeState> {
        return flow homeState@{
            emit(HomeState.Loading)
            delay(1000)
            val stateStat = stateDataDao.getStats()
            stateStat.collect {
                val dataSet = it

                var totalStat: StateData? = null
                val statesDataForUi = arrayListOf<CovidStat>()
                for (state in dataSet) {
                    if ("Total".equals(state.name, ignoreCase = true)) {
                        totalStat = state
                    } else {
                        statesDataForUi += CovidStat(
                            name = state.name,
                            confirmed = state.cases.toInt(),
                            active = state.active.toInt(),
                            recovered = state.recovered.toInt(),
                            deceased = state.deaths.toInt()
                        )
                    }
                }

                val confirmedStat =
                    StatCard(
                        totalStat?.cases ?: "0",
                        totalStat?.casesToday ?: "0",
                        arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                    )
                val activeStat =
                    StatCard(
                        totalStat?.active ?: "0",
                        "0",
                        arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                    )
                val recoveredStat =
                    StatCard(
                        totalStat?.recovered ?: "0",
                        totalStat?.recoveredToday ?: "0",
                        arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                    )
                val deceasedStat =
                    StatCard(
                        totalStat?.deaths ?: "0",
                        totalStat?.deathsToday ?: "0",
                        arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                    )
                val maxScale = arrayListOf(
                    confirmedStat.growthTrend.max()?.toFloat() ?: 1f,
                    activeStat.growthTrend.max()?.toFloat() ?: 1f,
                    recoveredStat.growthTrend.max()?.toFloat() ?: 1f,
                    deceasedStat.growthTrend.max()?.toFloat() ?: 1f
                ).max() ?: 1f

                emit(
                    HomeState.State(
                        placeName = "India",
                        lastUpdated = "Last updated 1 hour ago",
                        confirmed = confirmedStat,
                        active = activeStat,
                        recovered = recoveredStat,
                        deceased = deceasedStat,
                        growthTrendMaxScale = maxScale,
                        stats = statesDataForUi
                    )
                )
            }
        }
    }

}