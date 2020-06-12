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
    val active: String,
    val critical: String,
    val tests: String,
    val cases: String,
    val casesToday: String,
    val deaths: String,
    val deathsToday: String,
    val recovered: String,
    val recoveredToday: String,
    val testsPerMillion: String,
    val casesPerPeople: String,
    val deathsPerPeople: String,
    val activePerMillion: String,
    val recoveredPerMillion: String,
    val criticalPerMillion: String,
    val updated: Long
) {
    val lastUpdatedUiStr get() = DateUtil.getDateInFormat(updated)
}