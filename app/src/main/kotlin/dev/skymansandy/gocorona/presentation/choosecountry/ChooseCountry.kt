package dev.skymansandy.gocorona.presentation.choosecountry

import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryItem

sealed class ChooseCountryState {
    object Loading : ChooseCountryState()
    data class State(val countries: List<CountryItem>) : ChooseCountryState()
}

sealed class ChooseCountryEvent {

}