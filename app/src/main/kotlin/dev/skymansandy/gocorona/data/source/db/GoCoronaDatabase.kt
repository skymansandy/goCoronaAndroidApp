package dev.skymansandy.gocorona.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.CountryEntity
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity

@Database(
    entities = [
        CountryEntity::class,
        StateEntity::class,
        DistrictEntity::class
    ],
    version = 1
)
abstract class GoCoronaDatabase : RoomDatabase() {

    abstract fun districtDataDao(): DistrictDataDao

    abstract fun stateDataDao(): StateDataDao

    abstract fun countryDataDao(): CountryDataDao
}