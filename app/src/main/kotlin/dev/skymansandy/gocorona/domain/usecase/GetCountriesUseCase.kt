package dev.skymansandy.gocorona.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryState
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val getAllCountryUseCase: GetAllCountriesUseCase,
    private val getFilteredCountryUseCase: GetFilteredCountriesUseCase
) {

    operator fun invoke(searchQuery: String = ""): LiveData<ChooseCountryState> {
        return when (searchQuery.isEmpty()) {
            true -> getAllCountryUseCase().asLiveData()
            false -> getFilteredCountryUseCase(searchQuery).asLiveData()
        }
    }
}