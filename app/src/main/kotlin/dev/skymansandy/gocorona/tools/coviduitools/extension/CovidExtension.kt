package dev.skymansandy.gocorona.tools.coviduitools.extension

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.api.load
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidRes
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

fun TextView.showNumber(number: Int) {
    text = NumberFormat.getInstance().format(number)
}

fun TextView.scanForBigTextAndWrapNextLine() {
    val linearLayout = parent as? LinearLayout?
    linearLayout?.let {
        it.orientation = when (text.toString().length > 7) {
            true -> LinearLayout.VERTICAL
            false -> LinearLayout.HORIZONTAL
        }
    }
}

fun PieChart.loadData(covidRes: CovidRes, active: Int, recovered: Int, deceased: Int) {
    clearChart()
    addPieSlice(
        PieModel(resources.getString(R.string.active), active.toFloat(), covidRes.activeColor)
    )
    addPieSlice(
        PieModel(
            resources.getString(R.string.recovered),
            recovered.toFloat(),
            covidRes.recoveredColor
        )
    )
    addPieSlice(
        PieModel(resources.getString(R.string.deceased), deceased.toFloat(), covidRes.deceasedColor)
    )
    startAnimation()
}

@BindingAdapter("loadFlagUrl")
fun ImageView.loadFlag(flagUrl: String?) {
    flagUrl?.let {
        load(it) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
        }
    }
}

@BindingAdapter("loadFlagRes")
fun ImageView.loadFlagRes(@DrawableRes src: Int) {
    load(src) {
        crossfade(true)
        placeholder(R.mipmap.ic_launcher)
    }
}