package dev.skymansandy.gocorona.presentation.main.choosecountry

import dev.skymansandy.gocorona.presentation.main.choosecountry.adapter.CountryItem

sealed class ChooseCountryState {
    object Loading :
        ChooseCountryState()

    data class State(val countries: List<CountryItem>) :
        ChooseCountryState()
}

sealed class ChooseCountryEvent {
    data class SearchQuery(val searchQuery: String) :
        ChooseCountryEvent()
}