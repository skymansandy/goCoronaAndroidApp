package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.CovidTestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CovidTestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(covidTests: List<CovidTestEntity>?)

    @Query("SELECT * FROM CovidTest ORDER BY totalConfirmed DESC LIMIT 10")
    fun getLast90DaysStats(): Flow<List<CovidTestEntity>?>
}