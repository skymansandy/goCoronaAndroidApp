package dev.skymansandy.gocorona.di.module

import androidx.paging.PagedList
import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.repository.GoCoronaRepositoryImpl
import dev.skymansandy.gocorona.data.source.db.dao.*
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.presentation.main.india.adapter.CovidStatDataSource
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

    @Provides
    fun providePagingConfig(): PagedList.Config {
        return PagedList.Config.Builder()
            .setInitialLoadSizeHint(CovidStatDataSource.PAGE_SIZE)
            .setPageSize(CovidStatDataSource.PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }
}