package dev.skymansandy.gocorona.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skymansandy.gocorona.data.source.db.dao.StateDataDao
import dev.skymansandy.gocorona.data.source.db.entity.StateData

@Database(
    entities = [StateData::class],
    version = 1
)
abstract class AttendanceDatabase : RoomDatabase() {

    abstract fun stateDataDao(): StateDataDao
}