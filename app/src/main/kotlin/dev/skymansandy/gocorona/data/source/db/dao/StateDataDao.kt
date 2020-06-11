package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.StateData
import kotlinx.coroutines.flow.Flow

@Dao
interface StateDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stateDbList: StateData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stateDbList: List<StateData>?)

    @Query("SELECT * FROM statedata")
    fun getStats(): Flow<List<StateData>>
}