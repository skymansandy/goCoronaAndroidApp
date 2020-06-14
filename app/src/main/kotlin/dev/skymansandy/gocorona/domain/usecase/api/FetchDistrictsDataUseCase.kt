package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class FetchDistrictsDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke() {
        goCoronaRepository.fetchDistrictData()
            .enqueue(object : Callback<List<DistrictDataResponse>> {
                override fun onResponse(
                    call: Call<List<DistrictDataResponse>>,
                    response: Response<List<DistrictDataResponse>>
                ) {
                    val districtResponse = response.body()!!
                    val districtDbList = arrayListOf<DistrictEntity>()
                    districtResponse.map { state ->
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
                                deaths = district.deceased ?: 0,
                                deathsToday = district.delta.deceased ?: 0,
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

                override fun onFailure(call: Call<List<DistrictDataResponse>>, t: Throwable) {
                    Timber.tag("Tag").d(t.localizedMessage)
                }
            })
    }

}