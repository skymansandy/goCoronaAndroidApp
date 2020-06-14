package dev.skymansandy.gocorona.domain.usecase.api

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.source.db.entity.CovidTestEntity
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SaveStatesDataUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Deferred<StatesDataResponse> {
        return goCoronaRepository.fetchStatesDataAsync()
    }

    fun save(response: StatesDataResponse) {
        val stateDbList = response.statewise.map {
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
                deceased = it.deaths ?: 0,
                deceasedToday = it.deltadeaths ?: 0,
                recovered = it.recovered ?: 0,
                recoveredToday = it.deltarecovered ?: 0,
                migratedToOther = it.migratedother ?: 0,
                updated = timeMillis
            )
        }

        val testsDbList = response.cases_time_series.map {
            CovidTestEntity(
                date = it.date,
                totalConfirmed = it.totalconfirmed ?: 0,
                totalDeceased = it.totaldeceased ?: 0,
                totalRecovered = it.totalrecovered ?: 0
            )
        }

        CoroutineScope(Dispatchers.Default).launch {
            goCoronaRepository.insertStates(stateDbList)
            goCoronaRepository.insertCovidTests(testsDbList)
            Timber.tag("Tag").d("inserted ${stateDbList.size} states")
            Timber.tag("Tag").d("inserted ${testsDbList.size} tests")
        }
    }

}