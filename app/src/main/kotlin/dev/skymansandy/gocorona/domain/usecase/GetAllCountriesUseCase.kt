package dev.skymansandy.gocorona.domain.usecase

import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryState
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val goCoronaRepository: GoCoronaRepository
) {

    operator fun invoke(): Flow<ChooseCountryState> {
        return flow {
            emit(ChooseCountryState.Loading)
            goCoronaRepository.getCountries(false).collect {
                val dataSet = it
                val countries = arrayListOf<CountryItem>()
                for (countryData in dataSet) {
                    countries += CountryItem(
                        code = countryData.countryCode,
                        name = countryData.name,
                        flag = countryData.flag
                    )
                }
                emit(ChooseCountryState.State(countries))
            }
        }
    }
}