package dev.skymansandy.gocorona.domain.usecase

import android.util.Log
import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.entity.CountryData
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GetWorldDataFromApiUseCase @Inject constructor(
    private val goCoronaApi: GoCoronaApi,
    private val countryDataDao: CountryDataDao
) {

    operator fun invoke() {
        goCoronaApi.getCountryWiseData().enqueue(object : Callback<List<CountryWiseDataResponse>> {
            override fun onResponse(
                call: Call<List<CountryWiseDataResponse>>,
                response: Response<List<CountryWiseDataResponse>>
            ) {
                val countryData = response.body()
                val countryDbList = countryData?.map { it ->
                    CountryData(
                        countryCode = if (it.countryInfo.iso2.isNullOrEmpty())
                            it.country else it.countryInfo.iso2,
                        name = it.country,
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
                        updated = it.updated
                    )
                }
                CoroutineScope(Dispatchers.Default).launch {
                    countryDataDao.insertAll(countryDbList)
                    Log.d("Tag", "inserted ${countryDbList?.size} countries")
                }
            }

            override fun onFailure(call: Call<List<CountryWiseDataResponse>>, t: Throwable) {
                Log.d("Tag", t.localizedMessage)
            }
        })
    }

}