package dev.skymansandy.gocorona.data.source.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import dev.skymansandy.gocorona.data.source.db.entity.DistrictData

@Dao
interface DistrictDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(districtDbList: DistrictData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(districtDbList: List<DistrictData>?)
}