package dev.skymansandy.gocorona.presentation.choosecountry

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetCountriesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseCountryViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : BaseViewModel<ChooseCountryState, ChooseCountryEvent>() {

    private lateinit var liveData: LiveData<ChooseCountryState>

    init {
        viewModelScope.launch {
            getCountries()
        }
    }

    override fun onUserEvent(viewEvent: ChooseCountryEvent) {
        super.onUserEvent(viewEvent)
        when (viewEvent) {
            is ChooseCountryEvent.SearchQuery -> {
                getCountries(viewEvent.searchQuery)
            }
        }
    }

    private fun getCountries(searchQuery: String = "") {
        if (::liveData.isInitialized)
            mediator.removeSource(liveData)
        liveData = getCountriesUseCase(searchQuery)
        mediator.addSource(liveData) {
            viewState = it
        }
    }
}