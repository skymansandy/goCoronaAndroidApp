package dev.skymansandy.gocorona.data.repository

import dev.skymansandy.gocorona.presentation.home.HomeState
import kotlinx.coroutines.flow.Flow

interface GoCoronaRepository {

    fun getIndiaData(): Flow<HomeState>
}