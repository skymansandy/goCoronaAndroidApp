package dev.skymansandy.gocorona.presentation.home

import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat

sealed class HomeState {
    data class IndiaStats(
        val lastUpdated: String,
        val confirmed: StatCard,
        val active: StatCard,
        val recovered: StatCard,
        val deceased: StatCard,
        val growthTrendMaxScale: Float,
        val stats: List<CovidStat>?
    ) : HomeState()

    object Loading : HomeState()
}

sealed class HomeEvent

data class StatCard(
    var count: Int = 0,
    var deltaCount: Int = 0,
    var growthTrend: List<Int> = arrayListOf(0, 0)
)