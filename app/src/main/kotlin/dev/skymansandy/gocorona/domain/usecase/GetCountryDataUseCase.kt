package dev.skymansandy.gocorona.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.skymansandy.gocorona.presentation.home.HomeState
import javax.inject.Inject

class GetCountryDataUseCase @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase
) {

    operator fun invoke(): LiveData<HomeState> {
        return getIndiaDataForUiUseCase().asLiveData()
    }
}