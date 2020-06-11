package dev.skymansandy.gocorona.presentation.choosecountry

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetCountriesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChooseCountryViewModel @Inject constructor(
    getCountriesUseCase: GetCountriesUseCase
) : BaseViewModel<ChooseCountryState, ChooseCountryEvent>() {

    init {
        viewModelScope.launch {
            getCountriesUseCase().collect {
                viewState = it
            }
        }
    }

    override fun onUserEvent(viewEvent: ChooseCountryEvent) {
        super.onUserEvent(viewEvent)
        when (viewEvent) {
            is ChooseCountryEvent.SearchQuery -> {
                
            }
        }
    }
}