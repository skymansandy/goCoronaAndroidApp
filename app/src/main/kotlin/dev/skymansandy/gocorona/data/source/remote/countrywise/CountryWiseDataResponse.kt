package dev.skymansandy.gocorona.data.source.remote.countrywise

data class CountryWiseDataResponse(
    val updated: String,
    val country: String,
    val countryInfo: CountryInfo,
    val cases: String,
    val todayCases: String,
    val deaths: String,
    val todayDeaths: String,
    val recovered: String,
    val todayRecovered: String,
    val active: String,
    val critical: String,
    val casesPerOneMillion: String,
    val deathsPerOneMillion: String,
    val tests: String,
    val testsPerOneMillion: String,
    val population: String,
    val continent: String,
    val oneCasePerPeople: String,
    val oneDeathPerPeople: String,
    val oneTestPerPeople: String,
    val activePerOneMillion: String,
    val recoveredPerOneMillion: String,
    val criticalPerOneMillion: String
)

data class CountryInfo(
    val _id: String,
    val iso2: String,
    val iso3: String,
    val lat: String,
    val long: String,
    val flag: String
)