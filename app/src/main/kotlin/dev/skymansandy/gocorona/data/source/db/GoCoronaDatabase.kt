package dev.skymansandy.gocorona.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skymansandy.gocorona.data.source.db.dao.*
import dev.skymansandy.gocorona.data.source.db.entity.*

@Database(
    entities = [
        CountryEntity::class,
        StateEntity::class,
        DistrictEntity::class,
        WorldEntity::class,
        CovidTestEntity::class
    ],
    version = 1
)
abstract class GoCoronaDatabase : RoomDatabase() {

    abstract fun districtDataDao(): DistrictDao

    abstract fun stateDataDao(): StateDao

    abstract fun countryDataDao(): CountryDao

    abstract fun worldDataDao(): WorldDao

    abstract fun covidTestDao(): CovidTestDao
}