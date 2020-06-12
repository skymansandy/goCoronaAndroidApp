package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.dao.WorldDataDao
import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import dev.skymansandy.gocorona.data.source.db.entity.WorldEntity
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import javax.inject.Inject

class GoCoronaRepositoryImpl @Inject constructor(
    private val goCoronaApi: GoCoronaApi,
    private val stateDataDao: StateDataDao,
    private val worldDataDao: WorldDataDao,
    private val countryDataDao: CountryDataDao,
    private val districtDataDao: DistrictDataDao
) : GoCoronaRepository {

    override fun getStateStats(): Flow<List<StateEntity>> {
        return stateDataDao.getStats()
    }

    override fun getCountries(): Flow<List<CountryEntity>> {
        return countryDataDao.getStats()
    }

    override fun getCountryData(countryCode: String): Flow<CountryEntity?> {
        return countryDataDao.getCountry(countryCode)
    }

    override fun getWorldData(): Flow<WorldEntity?> {
        return worldDataDao.getWorldData()
    }

    override fun getDistrictDataForState(stateCode: String): Flow<List<DistrictEntity>?> {
        return districtDataDao.getDistrictsForState(stateCode)
    }

    override fun getDistrictData(districtCode: String): Flow<DistrictEntity?> {
        return districtDataDao.getDistrict(districtCode)
    }

    override fun getStateDetail(stateCode: String): Flow<StateEntity?> {
        return stateDataDao.getState(stateCode)
    }

    override suspend fun insertCountryApi(countryDbList: List<CountryEntity>?) {
        countryDataDao.insertAll(countryDbList)
    }

    override suspend fun insertDistricts(districtDbList: List<DistrictEntity>?) {
        districtDataDao.insertAll(districtDbList)
    }

    override suspend fun insertStates(stateDbList: List<StateEntity>) {
        stateDataDao.insertAll(stateDbList)
    }

    override suspend fun insertWorldData(worldEntity: WorldEntity) {
        worldDataDao.insert(worldEntity)
    }

    override fun fetchCountryWiseData(): Call<List<CountryWiseDataResponse>> {
        return goCoronaApi.getCountryWiseData()
    }

    override fun fetchDistrictData(): Call<List<DistrictDataResponse>> {
        return goCoronaApi.getDistrictData()
    }

    override fun fetchStatesData(): Call<StatesDataResponse> {
        return goCoronaApi.getStatesData()
    }

    override fun fetchWorldData(): Call<WorldDataResponse> {
        return goCoronaApi.getWorldData()
    }

}