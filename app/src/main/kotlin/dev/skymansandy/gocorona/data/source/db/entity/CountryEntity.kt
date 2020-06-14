package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.skymansandy.base.util.date.DateUtil

@Entity(tableName = "CountryData")
data class CountryEntity(
    @PrimaryKey
    val countryCode: String,
    val name: String,
    val flag: String,
    val active: Int,
    val critical: Int,
    val tests: Int,
    val cases: Int,
    val casesToday: Int,
    val deceased: Int,
    val deceasedToday: Int,
    val recovered: Int,
    val recoveredToday: Int,
    val updated: Long
) {
    val lastUpdatedUiStr get() = DateUtil.getDateInFormat(updated)
}