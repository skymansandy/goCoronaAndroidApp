package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.statedata.StateDataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStateDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(stateCode: String): Flow<StateDataState> {
        return flow homeState@{
            emit(StateDataState.Loading)
            delay(1000)
            val stateDetails = goCoronaRepository.getStateDetail(stateCode)
            val stateStat = goCoronaRepository.getDistrictDataForState(stateCode)
            stateDetails.combine(stateStat) { details, stat ->
                details?.let {
                    stat?.let { districts ->

                        val districtDataStatList = arrayListOf<CovidStat>()

                        for (district in districts) {
                            districtDataStatList += CovidStat(
                                code = district.code,
                                name = district.name,
                                confirmed = district.cases.toInt(),
                                active = district.active.toInt(),
                                recovered = district.recovered.toInt(),
                                deceased = district.deaths.toInt()
                            )
                        }

                        emit(
                            StateDataState.StateStats(
                                placeName = details.name,
                                lastUpdated = details.lastUpdatedUiStr,
                                active = details.active,
                                confirmed = details.cases,
                                confirmedToday = details.casesToday,
                                recovered = details.recovered,
                                recoveredToday = details.recoveredToday,
                                deaths = details.deaths,
                                deathsToday = details.deathsToday,
                                stats = districtDataStatList
                            )
                        )
                    }
                }
            }.collect()
        }
    }

}