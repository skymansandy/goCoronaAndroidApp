package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.data.source.db.entity.CountryData
import dev.skymansandy.gocorona.data.source.db.entity.DistrictData
import dev.skymansandy.gocorona.data.source.db.entity.StateData
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface GoCoronaRepository {

    //Room
    fun getStateStats(): Flow<List<StateData>>
    suspend fun insertCountryApi(countryDbList: List<CountryData>?)
    suspend fun insertDistricts(districtDbList: List<DistrictData>?)

    //Api
    fun fetchCountryWiseData(): Call<List<CountryWiseDataResponse>>
    fun fetchDistrictData(): Call<List<DistrictDataResponse>>
}