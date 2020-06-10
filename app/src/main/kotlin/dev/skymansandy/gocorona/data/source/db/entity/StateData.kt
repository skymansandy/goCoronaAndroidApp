package dev.skymansandy.gocorona.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StateData(
    @PrimaryKey
    val id: Long = 0L
)