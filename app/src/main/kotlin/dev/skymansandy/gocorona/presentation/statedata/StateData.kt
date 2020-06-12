package dev.skymansandy.gocorona.presentation.statedata

import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat

sealed class StateDataState {
    data class StateStats(
        val placeName: String,
        val lastUpdated: String,
        val active: String,
        val confirmed: String,
        val confirmedToday: String,
        val recovered: String,
        val recoveredToday: String,
        val deaths: String,
        val deathsToday: String,
        val stats: List<CovidStat>?
    ) : StateDataState()

    object Loading : StateDataState()
}

sealed class StateDataEvent {

}