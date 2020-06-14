package dev.skymansandy.gocorona.presentation.main.choosecountry

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetCountryListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseCountryViewModel @Inject constructor(
    private val getCountryListUseCase: GetCountryListUseCase
) : BaseViewModel<ChooseCountryState, ChooseCountryEvent>() {

    init {
        viewModelScope.launch {
            getCountries()
        }
    }

    private lateinit var liveData: LiveData<ChooseCountryState>

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
        liveData = getCountryListUseCase(searchQuery)
        mediator.addSource(liveData) {
            viewState = it
        }
    }
}