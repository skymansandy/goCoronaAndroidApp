package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import dev.skymansandy.gocorona.presentation.main.home.HomeState
import dev.skymansandy.gocorona.presentation.main.home.adapter.CovidStat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetIndiaDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Flow<HomeState> {
        return flow {
            emit(HomeState.Loading)
            val stateStat = goCoronaRepository.getStateStats()
            val testsStat = goCoronaRepository.getLatest90DaysCovidTests()
            stateStat.combine(testsStat) { states, tests ->
                tests?.let {
                    if (!states.isNullOrEmpty()) {
                        lateinit var totalStat: StateEntity
                        val statesDataForUi = arrayListOf<CovidStat>()
                        for (state in states) {
                            if ("Total".equals(state.name, ignoreCase = true)) {
                                totalStat = state
                            } else {
                                statesDataForUi += CovidStat(
                                    code = state.code,
                                    name = state.name,
                                    confirmed = state.cases,
                                    active = state.active,
                                    recovered = state.recovered,
                                    deceased = state.deceased
                                )
                            }
                        }

                        val trendConfirmedArr = arrayListOf<Float>()
                        val trendRecoveredArr = arrayListOf<Float>()
                        val trendDeceasedArr = arrayListOf<Float>()

                        if (!tests.isNullOrEmpty()) {
                            tests.reversed().map {
                                trendConfirmedArr += it.totalConfirmed.toFloat()
                                trendRecoveredArr += it.totalRecovered.toFloat()
                                trendDeceasedArr += it.totalDeceased.toFloat()
                            }
                        }

                        emit(
                            HomeState.IndiaStats(
                                lastUpdated = totalStat.lastUpdatedUiStr,
                                active = totalStat.active,
                                confirmed = totalStat.cases,
                                confirmedToday = totalStat.casesToday,
                                recovered = totalStat.recovered,
                                recoveredToday = totalStat.recoveredToday,
                                deceased = totalStat.deceased,
                                deceasedToday = totalStat.deceasedToday,
                                stats = statesDataForUi,
                                trendConfirmedCases = trendConfirmedArr,
                                trendRecoveredCases = trendRecoveredArr,
                                trendDeceasedCases = trendDeceasedArr
                            )
                        )
                    }
                }
            }.collect()
        }
    }
}