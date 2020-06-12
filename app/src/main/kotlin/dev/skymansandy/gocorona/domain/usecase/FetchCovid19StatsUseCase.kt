package dev.skymansandy.gocorona.domain.usecase

import android.util.Log
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
                            countryCode = if (it.countryInfo.iso2.isNullOrEmpty())
                                it.country else it.countryInfo.iso2,
                            name = it.country,
                            flag = it.countryInfo.flag,
                            active = it.active,
                            critical = it.critical,
                            tests = it.tests,
                            cases = it.cases,
                            casesToday = it.todayCases,
                            deaths = it.deaths,
                            deathsToday = it.todayDeaths,
                            recovered = it.recovered,
                            recoveredToday = it.todayRecovered,
                            testsPerMillion = it.testsPerOneMillion,
                            casesPerPeople = it.casesPerOneMillion,
                            deathsPerPeople = it.deathsPerOneMillion,
                            activePerMillion = it.activePerOneMillion,
                            recoveredPerMillion = it.recoveredPerOneMillion,
                            criticalPerMillion = it.criticalPerOneMillion,
                            updated = timeMillis
                        )
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertCountryApi(countryDbList)
                        Log.d("Tag", "inserted ${countryDbList?.size} countries")
                    }
                }

                override fun onFailure(call: Call<List<CountryWiseDataResponse>>, t: Throwable) {
                    Log.d("Tag", t.localizedMessage)
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
                                casesToday = district.delta.confirmed,
                                deaths = district.deceased,
                                deathsToday = district.delta.deceased,
                                recovered = district.recovered,
                                recoveredToday = district.delta.recovered,
                                updated = timeMillis
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

        goCoronaRepository.fetchStatesData()
            .enqueue(object : Callback<StatesDataResponse> {
                override fun onResponse(
                    call: Call<StatesDataResponse>,
                    response: Response<StatesDataResponse>
                ) {
                    val statesResponse = response.body()!!
                    val stateDbList = statesResponse.statewise.map { it ->
                        val timeMillis = try {
                            System.currentTimeMillis()
                        } catch (t: Throwable) {
                            System.currentTimeMillis()
                        }
                        StateEntity(
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
                            updated = timeMillis
                        )
                    }
                    CoroutineScope(Dispatchers.Default).launch {
                        goCoronaRepository.insertStates(stateDbList)
                        Log.d("Tag", "inserted ${stateDbList?.size} states")
                    }
                }

                override fun onFailure(call: Call<StatesDataResponse>, t: Throwable) {
                    Log.d("Tag", t.localizedMessage)
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
                                cases = worldData.cases,
                                todayCases = worldData.todayCases,
                                deaths = worldData.deaths,
                                todayDeaths = worldData.todayDeaths,
                                recovered = worldData.recovered,
                                todayRecovered = worldData.todayRecovered,
                                active = worldData.active,
                                critical = worldData.critical,
                                tests = worldData.tests,
                                testsPerOneMillion = worldData.testsPerOneMillion,
                                population = worldData.population,
                                updated = System.currentTimeMillis()
                            )
                        )
                        Log.d("Tag", "inserted World data")
                    }
                }

                override fun onFailure(call: Call<WorldDataResponse>, t: Throwable) {
                    Log.d("Tag", t.localizedMessage)
                }
            })
    }

}