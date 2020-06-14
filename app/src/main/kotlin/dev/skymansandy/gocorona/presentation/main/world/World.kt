package dev.skymansandy.gocorona.presentation.main.world

import dev.skymansandy.gocorona.presentation.main.india.adapter.CovidStat

sealed class WorldState {
    data class WorldStats(
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deceased: Int,
        val deceasedToday: Int,
        val stats: List<CovidStat>?
    ) : WorldState()

    object Loading : WorldState()
}

sealed class WorldEvent