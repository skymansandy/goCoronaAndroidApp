package dev.skymansandy.gocorona.presentation.world

import dev.skymansandy.gocorona.presentation.home.StatCard

sealed class WorldState {
    data class WorldStats(
        val placeName: String,
        val lastUpdated: String,
        val confirmed: StatCard,
        val active: StatCard,
        val recovered: StatCard,
        val deceased: StatCard
    ) : WorldState()

    object Loading : WorldState()
}

sealed class WorldEvent {
    data class CountryClicked(val countryId: String) : WorldEvent()
}