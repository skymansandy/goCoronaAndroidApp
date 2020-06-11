package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.skymansandy.gocorona.data.source.db.entity.CountryData

@Dao
interface CountryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryDbList: CountryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryDbList: List<CountryData>?)
}