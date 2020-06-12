package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.skymansandy.base.util.date.DateUtil

@Entity(tableName = "WorldData")
data class WorldEntity(
    @PrimaryKey
    val id: Long = 0L,
    val cases: String,
    val todayCases: String,
    val deaths: String,
    val todayDeaths: String,
    val recovered: String,
    val todayRecovered: String,
    val active: String,
    val critical: String,
    val tests: String,
    val testsPerOneMillion: String,
    val population: String,
    val updated: Long
) {
    val lastUpdatedUiStr get() = DateUtil.getDateInFormat(updated)
}