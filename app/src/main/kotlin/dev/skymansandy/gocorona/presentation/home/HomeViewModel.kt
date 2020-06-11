package dev.skymansandy.gocorona.presentation.home

import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.gocorona.domain.usecase.GetIndiaDistrictDataFromApiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetIndiaStatesDataFromApiUseCase
import dev.skymansandy.gocorona.domain.usecase.GetWorldDataFromApiUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val getCountryWiseDataFromApiUseCase: GetWorldDataFromApiUseCase,
    val getIndiaStatesDataFromApiUseCase: GetIndiaStatesDataFromApiUseCase,
    val getIndiaDistrictDataFromApiUseCase: GetIndiaDistrictDataFromApiUseCase
) : BaseViewModel<HomeState, HomeEvent>() {

    fun fetchCountryCases() {
        getCountryWiseDataFromApiUseCase()
        getIndiaStatesDataFromApiUseCase()
        getIndiaDistrictDataFromApiUseCase()
    }

}