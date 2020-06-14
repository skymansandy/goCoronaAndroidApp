package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.WorldEntity
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SaveWorldDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Deferred<WorldDataResponse> {
        return goCoronaRepository.fetchWorldDataAsync()
    }

    fun save(response: WorldDataResponse) {
        CoroutineScope(Dispatchers.Default).launch {
            goCoronaRepository.insertWorldData(
                WorldEntity(
                    cases = response.cases ?: 0,
                    casesToday = response.todayCases ?: 0,
                    deceased = response.deaths ?: 0,
                    deceasedToday = response.todayDeaths ?: 0,
                    recovered = response.recovered ?: 0,
                    recoveredToday = response.todayRecovered ?: 0,
                    active = response.active ?: 0,
                    critical = response.critical ?: 0,
                    tests = response.tests ?: 0,
                    testsPerOneMillion = response.testsPerOneMillion,
                    population = response.population ?: 0,
                    updated = System.currentTimeMillis()
                )
            )
            Timber.tag("Tag").d("inserted World data")
        }
    }
}