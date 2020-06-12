package dev.skymansandy.gocorona.presentation.world

import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat

sealed class WorldState {
    data class WorldStats(
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deaths: Int,
        val deathsToday: Int,
        val stats: List<CovidStat>?
    ) : WorldState()

    object Loading : WorldState()
}

sealed class WorldEvent