package dev.skymansandy.gocorona.presentation.main.home

import dev.skymansandy.gocorona.presentation.main.home.adapter.CovidStat

sealed class HomeState {
    data class IndiaStats(
        val lastUpdated: String,
        val active: Int,
        val confirmed: Int,
        val confirmedToday: Int,
        val recovered: Int,
        val recoveredToday: Int,
        val deaths: Int,
        val deathsToday: Int,
        val stats: List<CovidStat>?,
        var trendConfirmedCases: List<Float> = arrayListOf(0f, 0f),
        var trendRecoveredCases: List<Float> = arrayListOf(0f, 0f),
        var trendDeceasedCases: List<Float> = arrayListOf(0f, 0f)
    ) : HomeState()

    object Loading : HomeState()
}

sealed class HomeEvent
