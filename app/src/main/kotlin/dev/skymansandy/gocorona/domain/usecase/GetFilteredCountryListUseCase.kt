package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryState
import dev.skymansandy.gocorona.presentation.main.choosecountry.adapter.CountryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFilteredCountryListUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(searchQuery: String): Flow<ChooseCountryState> {
        return flow {
            emit(ChooseCountryState.Loading)
            goCoronaRepository.getFilteredCountries(searchQuery).collect {
                val dataSet = it
                val countries =
                    arrayListOf<CountryItem>()
                dataSet?.let { allCountries ->
                    for (countryData in allCountries) {
                        countries += CountryItem(
                            code = countryData.countryCode,
                            name = countryData.name,
                            flag = countryData.flag
                        )
                    }
                }
                emit(ChooseCountryState.State(countries))
            }
        }
    }
}