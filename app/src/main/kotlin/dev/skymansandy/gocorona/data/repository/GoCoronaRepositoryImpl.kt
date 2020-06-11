package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.domain.usecase.GetIndiaDataForUiUseCase
import dev.skymansandy.gocorona.presentation.home.HomeState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoCoronaRepositoryImpl @Inject constructor(
    private val getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase
) : GoCoronaRepository {

    override fun getIndiaData(): Flow<HomeState> {
        return getIndiaDataForUiUseCase()
    }

}