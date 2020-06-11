package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.presentation.home.HomeState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountryDataUseCase @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase,
    private val getOtherCountryDataForUiUseCase: GetOtherCountryDataForUiUseCase
) {

    operator fun invoke(countryCode: String): Flow<HomeState> {
        return when (countryCode) {
            "IN" -> getIndiaDataForUiUseCase()
            else -> getOtherCountryDataForUiUseCase(countryCode)
        }
    }
}