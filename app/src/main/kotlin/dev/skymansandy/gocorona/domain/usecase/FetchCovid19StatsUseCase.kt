package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import dev.skymansandy.gocorona.data.source.db.entity.WorldEntity
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class FetchCovid19StatsUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke() {
        goCoronaRepository.fetchCountryWiseData()
            .enqueue(object : Callback<List<CountryWiseDataResponse>> {
                override fun onResponse(
                    call: Call<List<CountryWiseDataResponse>>,
                    response: Response<List<CountryWiseDataResponse>>
                ) {
                    val countryData = response.body()
                    val countryDbList = countryData?.map { it ->

                        val timeMillis = try {
                            System.currentTimeMillis()
                        } catch (t: Throwable) {
                            System.currentTimeMillis()
                        }

                        CountryEntity(
                            countryCode = it.countryInfo.iso2 ?: it.country,
                            name = it.country,
                            flag = it.countryInfo.flag,
                            active = it.active ?: 0,
                            critical = it.critical ?: 0,
                            tests = it.tests ?: 0,
                            cases = it.cases ?: 0,
                            casesToday = it.todayCases ?: 0,
                            deaths = it.deaths ?: 0,
                            deathsToday = it.todayDeaths ?: 0,
                            recovered = it.recovered ?: 0,
                            recoveredToday = it.todayRecovered ?: 0,
                            updated = timeMillis
                        )
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertCountryApi(countryDbList)
                        Timber.tag("Tag").d("inserted ${countryDbList?.size} countries")
                    }
                }

                override fun onFailure(call: Call<List<CountryWiseDataResponse>>, t: Throwable) {
                    Timber.tag("Tag").d(t.localizedMessage)
                }
            })

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

        goCoronaRepository.fetchStatesData()
            .enqueue(object : Callback<StatesDataResponse> {
                override fun onResponse(
                    call: Call<StatesDataResponse>,
                    response: Response<StatesDataResponse>
                ) {
                    val statesResponse = response.body()!!
                    val stateDbList = statesResponse.statewise.map {
                        val timeMillis = try {
                            System.currentTimeMillis()
                        } catch (t: Throwable) {
                            System.currentTimeMillis()
                        }
                        StateEntity(
                            code = it.statecode,
                            name = it.state,
                            active = it.active ?: 0,
                            cases = it.confirmed ?: 0,
                            casesToday = it.deltaconfirmed ?: 0,
                            deaths = it.deaths ?: 0,
                            deathsToday = it.deltadeaths ?: 0,
                            recovered = it.recovered ?: 0,
                            recoveredToday = it.deltarecovered ?: 0,
                            migratedToOther = it.migratedother ?: 0,
                            updated = timeMillis
                        )
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertStates(stateDbList)
                        Timber.tag("Tag").d("inserted ${stateDbList.size} states")
                    }
                }

                override fun onFailure(call: Call<StatesDataResponse>, t: Throwable) {
                    Timber.tag("Tag").d(t.localizedMessage)
                }
            })

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
                                testsPerOneMillion = worldData.testsPerOneMillion ?: 0,
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