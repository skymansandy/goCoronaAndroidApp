package dev.skymansandy.gocorona.presentation.main.countrydata

sealed class CountryDataState {
    data class CountryStats(
        val placeName: String,
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deaths: Int,
        val deathsToday: Int
    ) : CountryDataState()

    object Loading : CountryDataState()
}

sealed class CountryDataEvent