package dev.skymansandy.gocorona.data.source.remote.countrywise

data class CountryWiseDataResponse(
    val updated: String,
    val country: String,
    val countryInfo: CountryInfo,
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
    val continent: String,
    val oneCasePerPeople: Float?,
    val oneDeathPerPeople: Float?,
    val oneTestPerPeople: Float?,
    val activePerOneMillion: Float?,
    val recoveredPerOneMillion: Float?,
    val criticalPerOneMillion: Float?
)

data class CountryInfo(
    val _id: String,
    val iso2: String?,
    val iso3: String,
    val lat: String,
    val long: String,
    val flag: String
)