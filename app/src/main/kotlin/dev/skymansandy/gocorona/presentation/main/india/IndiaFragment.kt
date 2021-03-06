package dev.skymansandy.gocorona.presentation.main.india

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.paging.PagedList
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentIndiaBinding
import dev.skymansandy.gocorona.databinding.LayoutStatCardBinding
import dev.skymansandy.gocorona.presentation.main.india.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber
import java.text.NumberFormat
import javax.inject.Inject

class IndiaFragment(override val layoutId: Int = R.layout.fragment_india) :
    BaseFragment<FragmentIndiaBinding, IndiaState, IndiaEvent, IndiaViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(requireContext()) }
    private val statAdapter = CovidStatListAdapter(this, CovidStatListType.STATE)

    @Inject
    lateinit var pageConfig: PagedList.Config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderViewState(newState: IndiaState) {
        binding.layoutLoading.visibility = View.GONE
        binding.statsIndia.root.visibility = View.GONE

        when (newState) {
            is IndiaState.Loading -> {
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is IndiaState.IndiaStats -> {
                binding.statsIndia.layoutStats.visibility = View.VISIBLE
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced), newState.lastUpdated)
                binding.statsIndia.layoutStatList.setup(
                    covidRes,
                    statAdapter,
                    getString(R.string.state_ut)
                )
                with(binding.statsIndia) {
                    tvActiveCount.text = NumberFormat.getInstance().format(newState.active)
                    statCardConfirmed.showStatCard(
                        getString(R.string.confirmed),
                        covidRes.confirmedColor,
                        newState.confirmed,
                        newState.confirmedToday,
                        newState.trendConfirmedCases
                    )
                    statCardRecovered.showStatCard(
                        getString(R.string.recovered),
                        covidRes.recoveredColor,
                        newState.recovered,
                        newState.recoveredToday,
                        newState.trendRecoveredCases
                    )
                    statCardDeceased.showStatCard(
                        getString(R.string.deceased),
                        covidRes.deceasedColor,
                        newState.deceased,
                        newState.deceasedToday,
                        newState.trendDeceasedCases
                    )
                    layoutStatList.root.visibility = if (newState.stats.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        val pagedStrings = getPagedList(newState.stats, pageConfig)
                        statAdapter.submitList(pagedStrings)
                        View.VISIBLE
                    }
                }
            }
        }
    }

    private fun LayoutStatCardBinding.showStatCard(
        title: String,
        color: Int,
        count: Int,
        delta: Int,
        trendArr: List<Float>
    ) {
        tvTitle.text = title
        tvCount.showNumber(count)
        showDelta(covidRes, tvDelta, delta)
        paintTextView(tvCount, color)
        paintTextView(tvDelta, color)

        snake.apply {
            clear()
            setStrokeColor(color)
            setMinValue(trendArr.min() ?: 0f)
            setMaxValue(trendArr.max() ?: 0f)
            visibility = if (!trendArr.isNullOrEmpty()) {
                trendArr.map { addValue(it) }
                View.VISIBLE
            } else View.GONE
        }
    }

    private fun paintTextView(textView: TextView, color: Int) {
        textView.setTextColor(color)
        textView.compoundDrawables[2]?.setTint(color)
        textView.compoundDrawables[2]?.setTint(color)
    }

    override fun onCountryClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) = TODO()
    override fun onStateClicked(covidStat: CovidStat) {
        navController.navigate(
            IndiaFragmentDirections.actionHomeFragmentToStateDataFragment(
                covidStat
            )
        )
    }
}