package dev.skymansandy.gocorona.tools.coviduitools.extension

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.widget.NestedScrollView
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

fun TextView.scanForBigTextAndWrapNextLine(): Boolean {
    val linearLayout = parent as? LinearLayout?
    return linearLayout?.let {
        val isLargeText = text.toString().length >= 7
        if (isLargeText) {
            it.orientation = LinearLayout.VERTICAL
        }
        isLargeText
    } ?: false
}

fun TextView.setOrientation(newOrientation: Int) {
    val linearLayout = parent as? LinearLayout?
    linearLayout?.let {
        it.orientation = newOrientation
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
            placeholder(R.color.gray)
        }
    }
}

@BindingAdapter("loadFlagRes")
fun ImageView.loadFlagRes(@DrawableRes src: Int) {
    load(src) {
        crossfade(true)
        placeholder(R.color.gray)
    }
}

fun NestedScrollView.setupForPaging() {
    viewTreeObserver.addOnScrollChangedListener {
        val view: View = getChildAt(childCount - 1) as View
        val diff: Int = view.bottom - (height + scrollY)
        if (diff == 0) {
            // your pagination code
        }
    }
}