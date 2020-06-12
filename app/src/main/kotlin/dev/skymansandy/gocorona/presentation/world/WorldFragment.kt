package dev.skymansandy.gocorona.presentation.world

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentWorldBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.home.HomeFragmentDirections
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class WorldFragment(override val layoutId: Int = R.layout.fragment_world) :
    BaseFragment<FragmentWorldBinding, WorldState, WorldEvent, WorldViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(activity!!) }
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

    private fun PieChart.loadData(countryData: WorldState.WorldStats) {
        clearChart()
        addPieSlice(
            PieModel("Active", countryData.active.count.toFloat(), covidRes.activeColor)
        )
        addPieSlice(
            PieModel("Recovered", countryData.recovered.count.toFloat(), covidRes.recoveredColor)
        )
        addPieSlice(
            PieModel("Deceased", countryData.deceased.count.toFloat(), covidRes.deceasedColor)
        )
        startAnimation()
    }

    override fun onCountryClicked(covidStat: CovidStat) {
        navController.navigate(
            HomeFragmentDirections.actionHomeFragmentToStateDataFragment(
                covidStat
            )
        )
    }

    override fun onStateClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) = TODO()
}