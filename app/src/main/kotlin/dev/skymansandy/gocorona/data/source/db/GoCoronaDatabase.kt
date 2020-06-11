package dev.skymansandy.gocorona.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skymansandy.gocorona.data.source.db.dao.CountryDataDao
import dev.skymansandy.gocorona.data.source.db.dao.DistrictDataDao
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.CountryData
import dev.skymansandy.gocorona.data.source.db.entity.DistrictData
import dev.skymansandy.gocorona.data.source.db.entity.StateData

@Database(
    entities = [
        CountryData::class,
        StateData::class,
        DistrictData::class
    ],
    version = 1
)
abstract class GoCoronaDatabase : RoomDatabase() {

    abstract fun districtDataDao(): DistrictDataDao

    abstract fun stateDataDao(): StateDataDao

    abstract fun countryDataDao(): CountryDataDao
}