package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skymansandy.gocorona.data.source.db.entity.DistrictEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistrictDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(districtDbList: DistrictEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(districtDbList: List<DistrictEntity>?)

    @Query("SELECT * from districtdata where stateCode=:stateCode")
    fun getDistrictsForState(stateCode: String): Flow<List<DistrictEntity>?>

    @Query("SELECT * from districtdata where code=:districtCode")
    fun getDistrict(districtCode: String): Flow<DistrictEntity?>
}