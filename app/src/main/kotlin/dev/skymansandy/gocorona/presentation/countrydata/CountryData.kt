package dev.skymansandy.gocorona.presentation.countrydata

sealed class CountryDataState {
    data class CountryStats(
        val placeName: String,
        val lastUpdated: String,
        val active: String,
        val confirmed: String,
        val confirmedToday: String,
        val recovered: String,
        val recoveredToday: String,
        val deaths: String,
        val deathsToday: String
    ) : CountryDataState()

    object Loading : CountryDataState()
}

sealed class CountryDataEvent