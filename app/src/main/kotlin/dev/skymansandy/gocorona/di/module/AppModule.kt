package dev.skymansandy.gocorona.di.module

import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.repository.GoCoronaRepositoryImpl
import dev.skymansandy.gocorona.data.source.db.dao.*
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesGoCoronaRepository(
        goCoronaApi: GoCoronaApi,
        stateDao: StateDao,
        worldDao: WorldDao,
        countryDao: CountryDao,
        districtDao: DistrictDao,
        covidTestDao: CovidTestDao
    ): GoCoronaRepository {
        return GoCoronaRepositoryImpl(
            goCoronaApi,
            stateDao,
            worldDao,
            countryDao,
            districtDao,
            covidTestDao
        )
    }
}