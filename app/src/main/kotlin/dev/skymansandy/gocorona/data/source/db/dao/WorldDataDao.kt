package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.WorldEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorldDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(worldData: WorldEntity)

    @Query("SELECT * FROM WorldData")
    fun getWorldData(): Flow<WorldEntity?>
}