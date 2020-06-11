package dev.skymansandy.gocorona.presentation.home

import androidx.lifecycle.viewModelScope
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) : BaseViewModel<HomeState, HomeEvent>() {

    init {
        viewModelScope.launch {
            goCoronaRepository.getIndiaData()
                .collect {
                    viewState = it
                }
        }
    }

}