package dev.skymansandy.gocorona.domain.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryState
import javax.inject.Inject

class GetCountryListUseCase @Inject constructor(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getFilteredCountryListUseCase: GetFilteredCountryListUseCase
) {

    operator fun invoke(searchQuery: String = ""): LiveData<ChooseCountryState> {
        return when (searchQuery.isEmpty()) {
            true -> getAllCountriesUseCase().asLiveData()
            false -> getFilteredCountryListUseCase(searchQuery).asLiveData()
        }
    }
}