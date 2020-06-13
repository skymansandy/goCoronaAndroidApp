package dev.skymansandy.gocorona.dagger.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.skymansandy.gocorona.data.source.db.GoCoronaDatabase
import dev.skymansandy.gocorona.data.source.db.dao.*
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun getAttendanceDatabase(application: Application): GoCoronaDatabase {
        return Room.databaseBuilder(
            application,
            GoCoronaDatabase::class.java,
            "go_corona_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun getCountryDataDao(attendanceDb: GoCoronaDatabase): CountryDao {
        return attendanceDb.countryDataDao()
    }

    @Provides
    @Singleton
    fun getStateDataDao(attendanceDb: GoCoronaDatabase): StateDao {
        return attendanceDb.stateDataDao()
    }

    @Provides
    @Singleton
    fun getWorldDataDao(attendanceDb: GoCoronaDatabase): WorldDao {
        return attendanceDb.worldDataDao()
    }

    @Provides
    @Singleton
    fun getDistrictDataDao(attendanceDb: GoCoronaDatabase): DistrictDao {
        return attendanceDb.districtDataDao()
    }

    @Provides
    @Singleton
    fun getCovidTestDao(attendanceDb: GoCoronaDatabase): CovidTestDao {
        return attendanceDb.covidTestDao()
    }

}