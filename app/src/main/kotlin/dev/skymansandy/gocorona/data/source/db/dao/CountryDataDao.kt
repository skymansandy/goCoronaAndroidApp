package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.CountryData
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryDbList: CountryData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countryDbList: List<CountryData>?)

    @Query("SELECT * FROM countrydata")
    fun getStats(): Flow<List<CountryData>>

    @Query("SELECT * FROM countrydata WHERE countryCode = :countryCode")
    fun getCountry(countryCode: String): Flow<CountryData?>
}