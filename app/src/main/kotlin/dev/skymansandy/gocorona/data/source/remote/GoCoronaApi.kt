package dev.skymansandy.gocorona.data.source.remote

import dev.skymansandy.gocorona.data.source.remote.brief.StatesDataResponse
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.DistrictDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GoCoronaApi {

    @GET("data.json")
    fun getStatesData(): Call<StatesDataResponse>

    @GET("v2/state_district_wise.json")
    fun getDistrictData(): Call<List<DistrictDataResponse>>

    @GET
    fun getCountryWiseData(
        @Url baseUrl: String = "https://corona.lmao.ninja/v2/countries"
    ): Call<List<CountryWiseDataResponse>>
}