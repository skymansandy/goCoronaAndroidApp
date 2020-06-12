package dev.skymansandy.gocorona.presentation.districtdata

sealed class DistrictDataState {
    data class DistrictStats(
        val placeName: String,
        val lastUpdated: String,
        val active: String,
        val confirmed: String,
        val confirmedToday: String,
        val recovered: String,
        val recoveredToday: String,
        val deaths: String,
        val deathsToday: String
    ) : DistrictDataState()

    object Loading : DistrictDataState()
}

sealed class DistrictDataEvent