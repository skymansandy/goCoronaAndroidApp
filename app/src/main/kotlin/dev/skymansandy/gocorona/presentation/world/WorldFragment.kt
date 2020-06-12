package dev.skymansandy.gocorona.presentation.world

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentWorldBinding
import dev.skymansandy.gocorona.databinding.LayoutStatCardBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.home.HomeFragmentDirections
import dev.skymansandy.gocorona.presentation.home.StatCard
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat
import kotlin.math.absoluteValue

class WorldFragment(override val layoutId: Int = R.layout.fragment_world) :
    BaseFragment<FragmentWorldBinding, WorldState, WorldEvent, WorldViewModel>(),
    CovidStatClickListener {

    private val confirmedColor get() = ContextCompat.getColor(activity!!, R.color.color_confirmed)
    private val activeColor get() = ContextCompat.getColor(activity!!, R.color.color_active)
    private val recoveredColor get() = ContextCompat.getColor(activity!!, R.color.color_recovered)
    private val deceasedColor get() = ContextCompat.getColor(activity!!, R.color.color_deceased)
    private val upDrawable
        get() = ContextCompat.getDrawable(
            activity!!,
            R.drawable.ic_baseline_arrow_upward_24
        )
    private val downDrawable
        get() = ContextCompat.getDrawable(
            activity!!,
            R.drawable.ic_baseline_arrow_downward_24
        )

    private val statAdapter = CovidStatAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }

        binding.tvPlace.setOnClickListener {
            ChooseCountryBottomSheet.getInstance(object : CountryClickListener {
                override fun onCountryClick(covidStat: CovidStat) {
                    binding.tvPlace.text = covidStat.name
                    vm.onUserEvent(WorldEvent.CountryClicked(covidStat.code))
                }
            }).show(childFragmentManager, "")
        }
    }

    override fun renderViewState(newState: WorldState) {
        binding.layoutLoading.visibility = View.GONE
        binding.statsIndia.root.visibility = View.GONE
        binding.statsNonIndia.root.visibility = View.GONE
        binding.swipe.isRefreshing = false

        when (newState) {
            is WorldState.Loading -> {
                binding.swipe.isRefreshing = true
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is WorldState.WorldStats -> {
                binding.swipe.isRefreshing = false
                binding.statsNonIndia.layoutStats.visibility = View.VISIBLE
                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text = "Last synced at ${newState.lastUpdated}"
                with(binding.statsNonIndia) {
                    tvActiveCount.text =
                        java.text.NumberFormat.getInstance().format(newState.active.count.toInt())
                    tvConfirmedCount.text =
                        java.text.NumberFormat.getInstance()
                            .format(newState.confirmed.count.toInt())
                    tvRecoveredCount.text =
                        java.text.NumberFormat.getInstance()
                            .format(newState.recovered.count.toInt())
                    tvDeceasedCount.text =
                        java.text.NumberFormat.getInstance().format(newState.deceased.count.toInt())
                    pieChart.loadData(newState)
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
                            downDrawable,
                            null
                        )
                    else ->
                        tvDelta.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            upDrawable,
                            null
                        )
                }
                View.VISIBLE
            } else View.GONE

        activity?.let {
            when (this) {
                binding.statsIndia.statCardConfirmed -> {
                    tvTitle.text = "Confirmed"
                    tvCount.setTextColor(confirmedColor)
                    tvDelta.setTextColor(confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(confirmedColor)
                    snake.setStrokeColor(confirmedColor)
                }
                binding.statsIndia.statCardRecovered -> {
                    tvTitle.text = "Recovered"
                    tvCount.setTextColor(recoveredColor)
                    tvDelta.setTextColor(recoveredColor)
                    tvDelta.compoundDrawables[2]?.setTint(recoveredColor)
                    snake.setStrokeColor(recoveredColor)
                }
                binding.statsIndia.statCardDeceased -> {
                    tvTitle.text = "Deceased"
                    tvCount.setTextColor(deceasedColor)
                    tvDelta.setTextColor(deceasedColor)
                    tvDelta.compoundDrawables[2]?.setTint(deceasedColor)
                    snake.setStrokeColor(deceasedColor)
                }
            }
        }
    }

    fun LayoutStatListBinding.setup(covidStatAdapter: CovidStatAdapter, title: String) {
        statList.adapter = covidStatAdapter
        statListHeader.tvTitle.text = title
        statListHeader.tvTitle.setTypeface(null, Typeface.BOLD)
        statListHeader.tvActive.setTypeface(null, Typeface.BOLD)
        statListHeader.tvConfirmed.setTypeface(null, Typeface.BOLD)
        statListHeader.tvRecovered.setTypeface(null, Typeface.BOLD)
        statListHeader.tvDeceased.setTypeface(null, Typeface.BOLD)
        statListHeader.tvActive.setTextColor(activeColor)
        statListHeader.tvConfirmed.setTextColor(confirmedColor)
        statListHeader.tvRecovered.setTextColor(recoveredColor)
        statListHeader.tvDeceased.setTextColor(deceasedColor)
    }

    private fun PieChart.loadData(countryData: WorldState.WorldStats) {
        clearChart()
        addPieSlice(
            PieModel("Active", countryData.active.count.toFloat(), activeColor)
        )
        addPieSlice(
            PieModel("Recovered", countryData.recovered.count.toFloat(), recoveredColor)
        )
        addPieSlice(
            PieModel("Deceased", countryData.deceased.count.toFloat(), deceasedColor)
        )
        startAnimation()
    }

    override fun onStateClicked(covidStat: CovidStat) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToStateDataFragment(
                covidStat
            )
        )
    }

    override fun onDistrictClicked(covidStat: CovidStat) = TODO()
}