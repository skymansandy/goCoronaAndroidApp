package dev.skymansandy.gocorona.tools.coviduitools.covidcolor

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import dev.skymansandy.gocorona.R

class CovidResImpl(private val context: Context) : CovidRes {

    override val confirmedColor get() = ContextCompat.getColor(context, R.color.color_confirmed)
    override val activeColor get() = ContextCompat.getColor(context, R.color.color_active)
    override val recoveredColor get() = ContextCompat.getColor(context, R.color.color_recovered)
    override val deceasedColor get() = ContextCompat.getColor(context, R.color.color_deceased)
    override val upDrawable: Drawable
        get() = ContextCompat.getDrawable(
            context, R.drawable.ic_baseline_arrow_upward_24
        )!!
    override val downDrawable: Drawable
        get() = ContextCompat.getDrawable(
            context, R.drawable.ic_baseline_arrow_downward_24
        )!!

}