package dev.skymansandy.gocorona.data.source.remote

import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import dev.skymansandy.gocorona.data.source.remote.worlddata.WorldDataResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

interface GoCoronaApi {

    @GET("data.json")
    fun getStatesDataAsync(): Deferred<StatesDataResponse>

    @GET("v2/state_district_wise.json")
    fun getDistrictDataAsync(): Deferred<List<DistrictDataResponse>>

    @GET
    fun getCountryWiseDataAsync(
        @Url baseUrl: String = "https://corona.lmao.ninja/v2/countries"
    ): Deferred<List<CountryWiseDataResponse>>

    @GET
    fun getWorldDataAsync(
        @Url baseUrl: String = "https://corona.lmao.ninja/v2/all"
    ): Deferred<WorldDataResponse>
}