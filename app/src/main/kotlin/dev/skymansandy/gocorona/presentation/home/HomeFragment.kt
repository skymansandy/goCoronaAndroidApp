package dev.skymansandy.gocorona.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import dev.skymansandy.gocorona.databinding.LayoutStatCardBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import java.text.NumberFormat
import kotlin.math.absoluteValue

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEvent, HomeViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(activity!!) }
    private val statAdapter = CovidStatAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }
    }

    override fun renderViewState(newState: HomeState) {
        binding.layoutLoading.visibility = View.GONE
        binding.statsIndia.root.visibility = View.GONE
        binding.swipe.isRefreshing = false

        when (newState) {
            is HomeState.Loading -> {
                binding.swipe.isRefreshing = true
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is HomeState.IndiaStats -> {
                binding.statsIndia.layoutStats.visibility = View.VISIBLE
                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text =
                    String.format("Last synced at %s", newState.lastUpdated)
                binding.statsIndia.layoutStatList.setup(statAdapter, "State/UT")
                with(binding.statsIndia) {
                    tvActiveCount.text =
                        NumberFormat.getInstance().format(newState.active.count.toInt())
                    statCardConfirmed.showStatCard(newState.confirmed, newState.growthTrendMaxScale)
                    statCardRecovered.showStatCard(newState.recovered, newState.growthTrendMaxScale)
                    statCardDeceased.showStatCard(newState.deceased, newState.growthTrendMaxScale)
                    layoutStatList.root.visibility = if (newState.stats.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        statAdapter.submitList(newState.stats)
                        View.VISIBLE
                    }
                }
            }
        }
    }

    private fun LayoutStatCardBinding.showStatCard(
        stat: StatCard,
        growthTrendMaxScale: Float
    ) {
        snake.clear()
        snake.setMaxValue(growthTrendMaxScale)
        snake.visibility = if (growthTrendMaxScale >= 0) {
            stat.growthTrend.map { snake.addValue(it.toFloat()) }
            View.VISIBLE
        } else View.GONE

        tvCount.text = NumberFormat.getInstance().format(stat.count.toInt())
        tvDelta.visibility =
            if (stat.deltaCount.toInt() != 0) {
                tvDelta.text =
                    NumberFormat.getInstance().format(stat.deltaCount.toInt().absoluteValue)
                when {
                    stat.deltaCount.toInt() < 0 ->
                        tvDelta.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            covidRes.downDrawable,
                            null
                        )
                    else ->
                        tvDelta.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            covidRes.upDrawable,
                            null
                        )
                }
                View.VISIBLE
            } else View.GONE

        activity?.let {
            when (this) {
                binding.statsIndia.statCardConfirmed -> {
                    tvTitle.text = "Confirmed"
                    tvCount.setTextColor(covidRes.confirmedColor)
                    tvDelta.setTextColor(covidRes.confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(covidRes.confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(covidRes.confirmedColor)
                    snake.setStrokeColor(covidRes.confirmedColor)
                }
                binding.statsIndia.statCardRecovered -> {
                    tvTitle.text = "Recovered"
                    tvCount.setTextColor(covidRes.recoveredColor)
                    tvDelta.setTextColor(covidRes.recoveredColor)
                    tvDelta.compoundDrawables[2]?.setTint(covidRes.recoveredColor)
                    snake.setStrokeColor(covidRes.recoveredColor)
                }
                binding.statsIndia.statCardDeceased -> {
                    tvTitle.text = "Deceased"
                    tvCount.setTextColor(covidRes.deceasedColor)
                    tvDelta.setTextColor(covidRes.deceasedColor)
                    tvDelta.compoundDrawables[2]?.setTint(covidRes.deceasedColor)
                    snake.setStrokeColor(covidRes.deceasedColor)
                }
            }
        }
    }

    override fun onStateClicked(covidStat: CovidStat) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToStateDataFragment(
                covidStat
            )
        )
    }

    override fun onCountryClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) = TODO()

    fun LayoutStatListBinding.setup(covidStatAdapter: CovidStatAdapter, title: String) {
        statList.adapter = covidStatAdapter
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
    }
}