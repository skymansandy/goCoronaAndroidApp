package dev.skymansandy.gocorona.presentation.main.india

import dev.skymansandy.gocorona.presentation.main.india.adapter.CovidStat

sealed class IndiaState {
    data class IndiaStats(
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deceased: Int,
        val deceasedToday: Int,
        val stats: List<CovidStat>?,
        var trendConfirmedCases: List<Float> = arrayListOf(0f, 0f),
        var trendRecoveredCases: List<Float> = arrayListOf(0f, 0f),
        var trendDeceasedCases: List<Float> = arrayListOf(0f, 0f)
    ) : IndiaState()

    object Loading : IndiaState()
}

sealed class IndiaEvent
