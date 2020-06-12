package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StateEntity(
    @PrimaryKey
    val code: String,
    val name: String,
    val active: String,
    val cases: String,
    val casesToday: String,
    val deaths: String,
    val deathsToday: String,
    val recovered: String,
    val recoveredToday: String,
    val migratedToOther: String,
    val updated: String
)