package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.main.india.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.main.india.state.StateDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetStateDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(stateCode: String): Flow<StateDataState> {
        return flow {
            emit(StateDataState.Loading)
            val stateDetails = goCoronaRepository.getStateDetail(stateCode)
            val stateStats = goCoronaRepository.getDistrictDataForState(stateCode)
            stateDetails.combine(stateStats) { details, stat ->
                details?.let {
                    stat?.let { districts ->

                        val districtDataStatList = arrayListOf<CovidStat>()

                        for (district in districts) {
                            districtDataStatList += CovidStat(
                                code = district.code,
                                name = district.name,
                                confirmed = district.cases,
                                active = district.active,
                                recovered = district.recovered,
                                deceased = district.deceased
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
                                deceased = details.deceased,
                                deceasedToday = details.deceasedToday,
                                stats = districtDataStatList
                            )
                        )
                    }
                }
            }.collect()
        }
    }

}