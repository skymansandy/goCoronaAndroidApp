package dev.skymansandy.gocorona.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.presentation.home.HomeState
import javax.inject.Inject

class GetCountryDataUseCase @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase,
    private val getWorldDataForUiUseCase: GetWorldDataForUiUseCase,
    private val getOtherCountryDataForUiUseCase: GetOtherCountryDataForUiUseCase
) {

    operator fun invoke(countryCode: String): LiveData<HomeState> {
        return when (countryCode) {
            AppConstant.IN -> getIndiaDataForUiUseCase().asLiveData()
            AppConstant.WORLDWIDE -> getWorldDataForUiUseCase().asLiveData()
            else -> getOtherCountryDataForUiUseCase(countryCode).asLiveData()
        }
    }
}