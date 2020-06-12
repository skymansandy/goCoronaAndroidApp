package dev.skymansandy.gocorona.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import dev.skymansandy.gocorona.databinding.LayoutStatCardBinding
import dev.skymansandy.gocorona.presentation.home.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.showLocaleNumber
import java.text.NumberFormat

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
        binding.swipe.isRefreshing = false
        binding.layoutLoading.visibility = View.GONE
        binding.statsIndia.root.visibility = View.GONE

        when (newState) {
            is HomeState.Loading -> {
                binding.swipe.isRefreshing = true
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is HomeState.IndiaStats -> {
                binding.tvPlace.text = newState.placeName
                binding.statsIndia.layoutStats.visibility = View.VISIBLE
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced_at), newState.lastUpdated)
                binding.statsIndia.layoutStatList.setup(
                    covidRes,
                    statAdapter,
                    getString(R.string.state_ut)
                )
                with(binding.statsIndia) {
                    tvActiveCount.text =
                        NumberFormat.getInstance().format(newState.active.count.toInt())
                    statCardConfirmed.showStatCard(
                        newState.confirmed, getString(R.string.confirmed),
                        covidRes.confirmedColor, newState.growthTrendMaxScale
                    )
                    statCardRecovered.showStatCard(
                        newState.recovered, getString(R.string.recovered),
                        covidRes.recoveredColor, newState.growthTrendMaxScale
                    )
                    statCardDeceased.showStatCard(
                        newState.deceased, getString(R.string.deceased),
                        covidRes.deceasedColor, newState.growthTrendMaxScale
                    )
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
        statCard: StatCard,
        title: String,
        color: Int,
        growthTrendMaxScale: Float
    ) {
        snake.clear()
        snake.setMaxValue(growthTrendMaxScale)
        snake.setStrokeColor(color)
        snake.visibility = if (growthTrendMaxScale >= 0) {
            statCard.growthTrend.map { snake.addValue(it.toFloat()) }
            View.VISIBLE
        } else View.GONE

        tvTitle.text = title
        tvCount.showLocaleNumber(statCard.count.toInt())
        showDelta(covidRes, tvDelta, statCard.deltaCount.toInt())
        paintTextView(tvCount, color)
        paintTextView(tvDelta, color)
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
            HomeFragmentDirections.actionHomeFragmentToStateDataFragment(
                covidStat
            )
        )
    }
}