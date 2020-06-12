package dev.skymansandy.gocorona.tools.coviduitools.covidcolor

import android.graphics.drawable.Drawable

interface CovidRes {
    val confirmedColor: Int
    val activeColor: Int
    val recoveredColor: Int
    val deceasedColor: Int
    val upDrawableArr: Drawable
    val downDrawableArr: Drawable
}