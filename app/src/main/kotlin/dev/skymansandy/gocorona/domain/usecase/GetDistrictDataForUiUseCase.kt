package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.main.india.district.DistrictDataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetDistrictDataForUiUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(districtCode: String): Flow<DistrictDataState> {
        return flow {
            emit(DistrictDataState.Loading)
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
                            deceased = it.deceased,
                            deceasedToday = it.deceasedToday
                        )
                    )
                }
            }
        }
    }

}