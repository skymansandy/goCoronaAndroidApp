package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class FetchCountryDataUseCase @Inject constructor(
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
                            deceased = it.deaths ?: 0,
                            deceasedToday = it.todayDeaths ?: 0,
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
    }

}