package dev.skymansandy.gocorona.presentation.home.adapter

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidRes
import java.text.NumberFormat
import kotlin.math.absoluteValue

fun LayoutStatListBinding.setup(
    covidRes: CovidRes,
    covidStatListAdapter: CovidStatListAdapter,
    title: String
) {
    statList.adapter = covidStatListAdapter
    statListHeader.tvTitle.text = title
    statListHeader.tvTitle.setTypeface(null, Typeface.BOLD)

    statListHeader.tvActive.setTypeface(null, Typeface.BOLD)
    statListHeader.tvConfirmed.setTypeface(null, Typeface.BOLD)
    statListHeader.tvRecovered.setTypeface(null, Typeface.BOLD)
    statListHeader.tvDeceased.setTypeface(null, Typeface.BOLD)

    statListHeader.tvActive.setTextColor(covidRes.activeColor)
    statListHeader.tvConfirmed.setTextColor(covidRes.confirmedColor)
    statListHeader.tvRecovered.setTextColor(covidRes.recoveredColor)
    statListHeader.tvDeceased.setTextColor(covidRes.deceasedColor)

    val headerSize = 14f
    statListHeader.tvActive.textSize = headerSize
    statListHeader.tvConfirmed.textSize = headerSize
    statListHeader.tvRecovered.textSize = headerSize
    statListHeader.tvDeceased.textSize = headerSize
}

fun showDelta(covidRes: CovidRes, textView: TextView, delta: Int) {
    textView.visibility =
        if (delta != 0) {
            textView.text = NumberFormat.getInstance().format(delta.absoluteValue)
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                if (delta < 0) covidRes.downDrawableArr else covidRes.upDrawableArr,
                null
            )
            View.VISIBLE
        } else View.INVISIBLE
}