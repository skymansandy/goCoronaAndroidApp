package dev.skymansandy.gocorona.dagger.module

import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.repository.GoCoronaRepositoryImpl
import dev.skymansandy.gocorona.domain.usecase.GetIndiaDataForUiUseCase

@Module
class AppModule {

    @Provides
    fun providesGoCoronaRepository(getIndiaDataForUiUseCase: GetIndiaDataForUiUseCase): GoCoronaRepository {
        return GoCoronaRepositoryImpl(getIndiaDataForUiUseCase)
    }
}