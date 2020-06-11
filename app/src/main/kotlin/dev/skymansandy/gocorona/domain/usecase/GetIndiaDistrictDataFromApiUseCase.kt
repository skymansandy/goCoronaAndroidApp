package dev.skymansandy.gocorona.domain.usecase

import android.util.Log
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.DistrictData
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetIndiaDistrictDataFromApiUseCase @Inject constructor(
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
                    val districtDbList = arrayListOf<DistrictData>()
                    districtResponse.map { state ->
                        state.districtData.map { district ->
                            districtDbList += DistrictData(
                                code = district.district,
                                stateCode = state.statecode,
                                name = district.district,
                                active = district.active,
                                cases = district.confirmed,
                                casesToday = district.delta.confirmed,
                                deaths = district.deceased,
                                deathsToday = district.delta.deceased,
                                recovered = district.recovered,
                                recoveredToday = district.delta.recovered,
                                updated = System.currentTimeMillis().toString()
                            )
                        }
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertDistricts(districtDbList)
                        Log.d("Tag", "inserted ${districtDbList?.size} Districts")
                    }
                }

                override fun onFailure(call: Call<List<DistrictDataResponse>>, t: Throwable) {
                    Log.d("Tag", t.localizedMessage)
                }
            })
    }
}