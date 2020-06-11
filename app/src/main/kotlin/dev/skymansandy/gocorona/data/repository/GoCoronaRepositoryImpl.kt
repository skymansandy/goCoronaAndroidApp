package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.CountryData
import dev.skymansandy.gocorona.data.source.db.entity.DistrictData
import dev.skymansandy.gocorona.data.source.db.entity.StateData
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import javax.inject.Inject

class GoCoronaRepositoryImpl @Inject constructor(
    private val goCoronaApi: GoCoronaApi,
    private val stateDataDao: StateDataDao,
    private val countryDataDao: CountryDataDao,
    private val districtDataDao: DistrictDataDao
) : GoCoronaRepository {

    override fun getStateStats(): Flow<List<StateData>> {
        return stateDataDao.getStats()
    }

    override suspend fun insertCountryApi(countryDbList: List<CountryData>?) {
        countryDataDao.insertAll(countryDbList)
    }

    override suspend fun insertDistricts(districtDbList: List<DistrictData>?) {
        districtDataDao.insertAll(districtDbList)
    }

    override suspend fun insertStates(stateDbList: List<StateData>) {
        stateDataDao.insertAll(stateDbList)
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

}