package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SaveCountryDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Deferred<List<CountryWiseDataResponse>> {
        return goCoronaRepository.fetchCountryWiseDataAsync()
    }

    fun save(response: List<CountryWiseDataResponse>) {
        val countryDbList = response.map { it ->
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

}