package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.StateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stateDbList: StateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stateDbList: List<StateEntity>?)

    @Query("SELECT * FROM StateData ORDER BY cases DESC")
    fun getStats(): Flow<List<StateEntity>>

    @Query("SELECT * FROM StateData WHERE code=:stateCode")
    fun getState(stateCode: String): Flow<StateEntity?>
}