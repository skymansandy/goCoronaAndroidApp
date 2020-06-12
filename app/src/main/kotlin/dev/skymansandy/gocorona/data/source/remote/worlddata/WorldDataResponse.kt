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
    val casesPerOneMillion: String,
    val deathsPerOneMillion: String,
    val tests: Int?,
    val testsPerOneMillion: String,
    val population: Long?,
    val oneCasePerPeople: String,
    val oneDeathPerPeople: String,
    val oneTestPerPeople: String,
    val activePerOneMillion: String,
    val recoveredPerOneMillion: String,
    val criticalPerOneMillion: String,
    val affectedCountries: Int?
)