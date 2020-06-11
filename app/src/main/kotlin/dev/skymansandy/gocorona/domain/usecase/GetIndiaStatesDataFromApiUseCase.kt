package dev.skymansandy.gocorona.domain.usecase

import android.util.Log
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.StateData
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetIndiaStatesDataFromApiUseCase @Inject constructor(
    private val goCoronaApi: GoCoronaApi,
    private val stateDataDao: StateDataDao,
    private val districtDataDao: DistrictDataDao
) {

    operator fun invoke() {
        goCoronaApi.getStatesData().enqueue(object : Callback<StatesDataResponse> {
            override fun onResponse(
                call: Call<StatesDataResponse>,
                response: Response<StatesDataResponse>
            ) {
                val statesResponse = response.body()!!
                val stateDbList = statesResponse.statewise.map { it ->
                    StateData(
                        code = it.statecode,
                        name = it.state,
                        active = it.active,
                        cases = it.confirmed,
                        casesToday = it.deltaconfirmed,
                        deaths = it.deaths,
                        deathsToday = it.deltadeaths,
                        recovered = it.recovered,
                        recoveredToday = it.deltarecovered,
                        migratedToOther = it.migratedother,
                        updated = it.lastupdatedtime
                    )
                }
                CoroutineScope(Dispatchers.Default).launch {
                    stateDataDao.insertAll(stateDbList)
                    Log.d("Tag", "inserted ${stateDbList?.size} states")
                }
            }

            override fun onFailure(call: Call<StatesDataResponse>, t: Throwable) {
                Log.d("Tag", t.localizedMessage)
            }
        })
    }
}