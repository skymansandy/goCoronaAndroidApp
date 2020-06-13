package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.CovidTestEntity

@Dao
interface CovidTestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(covidTestEntities: List<CovidTestEntity>?)

    @Query("SELECT * FROM CovidTest")
    suspend fun getAll(): List<CovidTestEntity>?
}