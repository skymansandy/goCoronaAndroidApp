package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SaveDistrictsDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Deferred<List<DistrictDataResponse>> {
        return goCoronaRepository.fetchDistrictDataAsync()
    }

    fun save(response: List<DistrictDataResponse>) {
        val districtDbList = arrayListOf<DistrictEntity>()
        response.map { state ->
            state.districtData.map { district ->
                val timeMillis = try {
                    System.currentTimeMillis()
                } catch (t: Throwable) {
                    System.currentTimeMillis()
                }
                districtDbList += DistrictEntity(
                    code = district.district,
                    stateCode = state.statecode,
                    name = district.district,
                    active = district.active,
                    cases = district.confirmed,
                    casesToday = district.delta.confirmed ?: 0,
                    deceased = district.deceased ?: 0,
                    deceasedToday = district.delta.deceased ?: 0,
                    recovered = district.recovered ?: 0,
                    recoveredToday = district.delta.recovered ?: 0,
                    updated = timeMillis
                )
            }
        }
        CoroutineScope(Dispatchers.Default).launch {
            goCoronaRepository.insertDistricts(districtDbList)
            Timber.tag("Tag").d("inserted ${districtDbList.size} Districts")
        }
    }

}