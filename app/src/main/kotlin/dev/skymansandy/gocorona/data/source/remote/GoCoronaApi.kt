package dev.skymansandy.gocorona.data.source.remote

import dev.skymansandy.gocorona.data.source.remote.brief.BriefDataResponse
import dev.skymansandy.gocorona.data.source.remote.statewise.StateWiseDataResponse
import retrofit2.Call
import retrofit2.http.GET

interface GoCoronaApi {

    @GET("data.json")
    fun getBriefData(): Call<BriefDataResponse>

    @GET("v2/state_district_wise.json")
    fun getStateWiseData(): Call<StateWiseDataResponse>
}