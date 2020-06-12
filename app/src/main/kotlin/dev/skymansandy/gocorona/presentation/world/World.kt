package dev.skymansandy.gocorona.presentation.world

import dev.skymansandy.gocorona.presentation.home.StatCard
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat

sealed class WorldState {
    data class WorldStats(
        val lastUpdated: String,
        val confirmed: StatCard,
        val active: StatCard,
        val recovered: StatCard,
        val deceased: StatCard,
        val stats: List<CovidStat>?
    ) : WorldState()

    object Loading : WorldState()
}

sealed class WorldEvent