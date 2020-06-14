package dev.skymansandy.gocorona.presentation.main.india.state

import dev.skymansandy.gocorona.presentation.main.india.adapter.CovidStat

sealed class StateDataState {
    data class StateStats(
        val placeName: String,
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deceased: Int,
        val deceasedToday: Int,
        val stats: List<CovidStat>?
    ) : StateDataState()

    object Loading : StateDataState()
}

sealed class StateDataEvent