package dev.skymansandy.gocorona.presentation.main.statedata

import dev.skymansandy.gocorona.presentation.main.home.adapter.CovidStat

sealed class StateDataState {
    data class StateStats(
        val placeName: String,
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deaths: Int,
        val deathsToday: Int,
        val stats: List<CovidStat>?
    ) : StateDataState()

    object Loading : StateDataState()
}

sealed class StateDataEvent