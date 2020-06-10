package dev.skymansandy.base.util.math

object DistanceUtil {
    fun cmToMm(cm: Float) = cm * 10

    fun cmToMm(cm: Int) = cm * 10.0f

    fun mmToCm(mm: Float) = mm / 10

    fun mmToCm(mm: Int) = mm / 10.0f
}