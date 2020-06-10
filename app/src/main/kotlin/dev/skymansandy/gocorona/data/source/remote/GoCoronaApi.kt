package dev.skymansandy.gocorona.data.source.remote

import retrofit2.http.GET

interface GoCoronaApi {

    @GET("data.json")
    fun getBriefData(): String

    @GET("v2/state_district_wise.json")
    fun getStateWiseData(): String
}