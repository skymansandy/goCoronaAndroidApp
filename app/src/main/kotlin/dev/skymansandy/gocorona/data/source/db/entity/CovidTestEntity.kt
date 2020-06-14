package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CovidTest")
data class CovidTestEntity(
    @PrimaryKey
    val date: String,
    val totalConfirmed: Int,
    val totalDeceased: Int,
    val totalRecovered: Int
)