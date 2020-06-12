package dev.skymansandy.gocorona.dagger.module

import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.data.repository.GoCoronaRepository
import dev.skymansandy.gocorona.data.repository.GoCoronaRepositoryImpl
import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.dao.WorldDataDao
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesGoCoronaRepository(
        goCoronaApi: GoCoronaApi,
        stateDataDao: StateDataDao,
        worldDataDao: WorldDataDao,
        countryDataDao: CountryDataDao,
        districtDataDao: DistrictDataDao
    ): GoCoronaRepository {
        return GoCoronaRepositoryImpl(
            goCoronaApi,
            stateDataDao,
            worldDataDao,
            countryDataDao,
            districtDataDao
        )
    }
}