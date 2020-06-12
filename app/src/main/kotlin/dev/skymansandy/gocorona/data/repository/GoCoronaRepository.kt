package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface GoCoronaRepository {

    //Room
    fun getStateStats(): Flow<List<StateEntity>>
    fun getCountries(): Flow<List<CountryEntity>>
    fun getCountryData(countryCode: String): Flow<CountryEntity?>
    fun getDistrictDataForState(stateCode: String): Flow<List<DistrictEntity>?>
    fun getDistrictData(districtCode: String): Flow<DistrictEntity?>
    fun getStateDetail(stateCode: String): Flow<StateEntity?>

    suspend fun insertCountryApi(countryDbList: List<CountryEntity>?)
    suspend fun insertDistricts(districtDbList: List<DistrictEntity>?)
    suspend fun insertStates(stateDbList: List<StateEntity>)

    //Api
    fun fetchCountryWiseData(): Call<List<CountryWiseDataResponse>>
    fun fetchDistrictData(): Call<List<DistrictDataResponse>>
    fun fetchStatesData(): Call<StatesDataResponse>
}