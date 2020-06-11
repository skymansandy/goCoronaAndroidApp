package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.skymansandy.gocorona.data.source.db.entity.StateData

@Dao
interface StateDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stateDbList: StateData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stateDbList: List<StateData>?)
}