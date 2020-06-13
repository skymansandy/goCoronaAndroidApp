package dev.skymansandy.gocorona.presentation.health

import dev.skymansandy.gocorona.presentation.health.adapter.ImageItem

data class HealthState(val str: String)

sealed class HealthEvent {

}

val healthTipsArray = arrayListOf<ImageItem>(
    ImageItem(),
    ImageItem(),
    ImageItem(),
    ImageItem(),
    ImageItem(),
    ImageItem()
)