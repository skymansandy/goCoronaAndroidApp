package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.data.source.db.dao.*
import dev.skymansandy.gocorona.data.source.db.entity.*
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoCoronaRepositoryImpl @Inject constructor(
    private val goCoronaApi: GoCoronaApi,
    private val stateDao: StateDao,
    private val worldDao: WorldDao,
    private val countryDao: CountryDao,
    private val districtDao: DistrictDao,
    private val covidTestDao: CovidTestDao
) : GoCoronaRepository {

    override fun getStateStats(): Flow<List<StateEntity>> {
        return stateDao.getStats()
    }

    override fun getCountries(sortedByCaseCount: Boolean): Flow<List<CountryEntity>> {
        return when (sortedByCaseCount) {
            true -> countryDao.getStatsSortedByCases()
            false -> countryDao.getStats()
        }
    }

    override fun getFilteredCountries(searchQuery: String): Flow<List<CountryEntity>?> {
        return countryDao.getFilteredCountries("%$searchQuery%")
    }

    override fun getCountryData(countryCode: String): Flow<CountryEntity?> {
        return countryDao.getCountry(countryCode)
    }

    override fun getWorldData(): Flow<WorldEntity?> {
        return worldDao.getWorldData()
    }

    override fun getDistrictDataForState(stateCode: String): Flow<List<DistrictEntity>?> {
        return districtDao.getDistrictsForState(stateCode)
    }

    override fun getDistrictData(districtCode: String): Flow<DistrictEntity?> {
        return districtDao.getDistrict(districtCode)
    }

    override fun getStateDetail(stateCode: String): Flow<StateEntity?> {
        return stateDao.getState(stateCode)
    }

    override fun getLatest90DaysCovidTests(): Flow<List<CovidTestEntity>?> {
        return covidTestDao.getLast90DaysStats()
    }

    override suspend fun insertCountryApi(countryDbList: List<CountryEntity>?) {
        countryDao.insertAll(countryDbList)
    }

    override suspend fun insertDistricts(districtDbList: List<DistrictEntity>?) {
        districtDao.insertAll(districtDbList)
    }

    override suspend fun insertStates(stateDbList: List<StateEntity>) {
        stateDao.insertAll(stateDbList)
    }

    override suspend fun insertWorldData(worldEntity: WorldEntity) {
        worldDao.insert(worldEntity)
    }

    override suspend fun insertCovidTests(list: List<CovidTestEntity>?) {
        covidTestDao.insertAll(list)
    }

    override fun fetchCountryWiseDataAsync(): Deferred<List<CountryWiseDataResponse>> {
        return goCoronaApi.getCountryWiseDataAsync()
    }

    override fun fetchDistrictDataAsync(): Deferred<List<DistrictDataResponse>> {
        return goCoronaApi.getDistrictDataAsync()
    }

    override fun fetchStatesDataAsync(): Deferred<StatesDataResponse> {
        return goCoronaApi.getStatesDataAsync()
    }

    override fun fetchWorldDataAsync(): Deferred<WorldDataResponse> {
        return goCoronaApi.getWorldDataAsync()
    }

}