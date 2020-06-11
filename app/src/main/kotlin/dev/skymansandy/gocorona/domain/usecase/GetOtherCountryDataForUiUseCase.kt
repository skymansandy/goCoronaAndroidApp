package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.HomeState
import dev.skymansandy.gocorona.presentation.home.StatCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOtherCountryDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(countryCode: String): Flow<HomeState> {
        return flow homeState@{
            emit(HomeState.Loading)
            delay(1000)
            val countriesStat = goCoronaRepository.getCountryData(countryCode)
            countriesStat.collect {
                it?.let {
                    val countryData = it

                    val confirmedStat =
                        StatCard(
                            countryData.cases, countryData.casesToday,
                            arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                        )
                    val activeStat =
                        StatCard(
                            countryData.active, "0",
                            arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                        )
                    val recoveredStat =
                        StatCard(
                            countryData.recovered, countryData.recoveredToday,
                            arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                        )
                    val deceasedStat =
                        StatCard(
                            countryData.deaths, countryData.deathsToday,
                            arrayListOf(0, 1, 2, 4, 56, 123, 465, 3210)
                        )

                    emit(
                        HomeState.State(
                            placeName = countryData.name,
                            lastUpdated = countryData.updated,
                            confirmed = confirmedStat,
                            active = activeStat,
                            recovered = recoveredStat,
                            deceased = deceasedStat,
                            growthTrendMaxScale = -1f,
                            stats = null
                        )
                    )
                }
            }
        }
    }

}