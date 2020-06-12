package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.skymansandy.base.util.date.DateUtil

@Entity(tableName = "StateData")
data class StateEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val active: Int,
    val cases: Int,
    val casesToday: Int,
    val deaths: Int,
    val deathsToday: Int,
    val recovered: Int,
    val recoveredToday: Int,
    val migratedToOther: Int,
    val updated: Long
) {
    val lastUpdatedUiStr get() = DateUtil.getDateInFormat(updated)
}