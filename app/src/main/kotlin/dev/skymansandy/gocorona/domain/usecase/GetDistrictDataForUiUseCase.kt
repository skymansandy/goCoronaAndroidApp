package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.districtdata.DistrictDataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDistrictDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(districtCode: String): Flow<DistrictDataState> {
        return flow homeState@{
            emit(DistrictDataState.Loading)
            delay(1000)
            val stateStat = goCoronaRepository.getDistrictData(districtCode)
            stateStat.collect {
                it?.let {
                    emit(
                        DistrictDataState.DistrictStats(
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