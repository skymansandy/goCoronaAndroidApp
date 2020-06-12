package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.skymansandy.base.util.date.DateUtil

@Entity(
    tableName = "DistrictData"
    /*foreignKeys = [ForeignKey(
        entity = StateData::class,
        parentColumns = ["code"],
        childColumns = ["stateCode"],
        onDelete = ForeignKey.RESTRICT
    )]*/
)
data class DistrictEntity(
    @PrimaryKey
    val code: String,
    val stateCode: String,
    val name: String,
    val active: String,
    val cases: String,
    val casesToday: String,
    val deaths: String,
    val deathsToday: String,
    val recovered: String,
    val recoveredToday: String,
    val updated: Long
){
    val lastUpdatedUiStr get() = DateUtil.getDateInFormat(updated)
}