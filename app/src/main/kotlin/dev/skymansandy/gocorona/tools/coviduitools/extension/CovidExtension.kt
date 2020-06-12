package dev.skymansandy.gocorona.tools.coviduitools.extension

import android.widget.TextView
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidRes
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

fun TextView.showLocaleNumber(number: Int) {
    text = NumberFormat.getInstance().format(number)
}

fun PieChart.loadData(covidRes: CovidRes, active: Int, recovered: Int, deceased: Int) {
    clearChart()
    addPieSlice(
        PieModel("Active", active.toFloat(), covidRes.activeColor)
    )
    addPieSlice(
        PieModel("Recovered", recovered.toFloat(), covidRes.recoveredColor)
    )
    addPieSlice(
        PieModel("Deceased", deceased.toFloat(), covidRes.deceasedColor)
    )
    startAnimation()
}