package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.WorldEntity
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class FetchWorldDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke() {
        goCoronaRepository.fetchWorldData()
            .enqueue(object : Callback<WorldDataResponse> {
                override fun onResponse(
                    call: Call<WorldDataResponse>,
                    response: Response<WorldDataResponse>
                ) {
                    val worldData = response.body()!!
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertWorldData(
                            WorldEntity(
                                cases = worldData.cases ?: 0,
                                todayCases = worldData.todayCases ?: 0,
                                deaths = worldData.deaths ?: 0,
                                todayDeaths = worldData.todayDeaths ?: 0,
                                recovered = worldData.recovered ?: 0,
                                todayRecovered = worldData.todayRecovered ?: 0,
                                active = worldData.active ?: 0,
                                critical = worldData.critical ?: 0,
                                tests = worldData.tests ?: 0,
                                testsPerOneMillion = worldData.testsPerOneMillion,
                                population = worldData.population ?: 0,
                                updated = System.currentTimeMillis()
                            )
                        )
                        Timber.tag("Tag").d("inserted World data")
                    }
                }

                override fun onFailure(call: Call<WorldDataResponse>, t: Throwable) {
                    Timber.tag("Tag").d(t.localizedMessage)
                }
            })
    }

}