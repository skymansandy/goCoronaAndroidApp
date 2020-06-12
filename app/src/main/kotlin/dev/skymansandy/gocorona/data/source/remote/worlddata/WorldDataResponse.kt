package dev.skymansandy.gocorona.data.source.remote.worlddata

data class WorldDataResponse(
    val updated: String,
    val cases: Int?,
    val todayCases: Int?,
    val deaths: Int?,
    val todayDeaths: Int?,
    val recovered: Int?,
    val todayRecovered: Int?,
    val active: Int?,
    val critical: Int?,
    val casesPerOneMillion: Int?,
    val deathsPerOneMillion: Int?,
    val tests: Int?,
    val testsPerOneMillion: Int?,
    val population: Long?,
    val oneCasePerPeople: Int?,
    val oneDeathPerPeople: Int?,
    val oneTestPerPeople: Int?,
    val activePerOneMillion: Int?,
    val recoveredPerOneMillion: Int?,
    val criticalPerOneMillion: Int?,
    val affectedCountries: Int?
)