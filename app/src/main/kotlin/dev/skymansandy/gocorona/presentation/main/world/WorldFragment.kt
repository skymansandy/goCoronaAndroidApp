package dev.skymansandy.gocorona.presentation.main.world

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentWorldBinding
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.main.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.main.home.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.setOrientation
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber

class WorldFragment(override val layoutId: Int = R.layout.fragment_world) :
    BaseFragment<FragmentWorldBinding, WorldState, WorldEvent, WorldViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(requireContext()) }
    private val statAdapter = CovidStatListAdapter(this, CovidStatListType.COUNTRY)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }

        binding.ivSearchCountries.setOnClickListener {
            ChooseCountryBottomSheet.getInstance(
                object : CountryClickListener {
                    override fun onCountryClick(covidStat: CovidStat) {
                        onCountryClicked(covidStat)
                    }
                }).show(childFragmentManager, "")
        }
    }

    override fun renderViewState(newState: WorldState) {
        binding.layoutLoading.visibility = View.GONE
        binding.statsWorld.root.visibility = View.GONE
        binding.layoutStatList.root.visibility = View.GONE
        binding.swipe.isRefreshing = false

        when (newState) {
            is WorldState.Loading -> {
                binding.swipe.isRefreshing = true
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is WorldState.WorldStats -> {
                binding.swipe.isRefreshing = false
                binding.statsWorld.layoutStats.visibility = View.VISIBLE
                binding.layoutStatList.root.visibility = View.VISIBLE
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced), newState.lastUpdated)
                binding.layoutStatList.setup(covidRes, statAdapter, getString(R.string.country))
                with(binding.statsWorld) {
                    tvActiveCount.showNumber(newState.active)
                    tvConfirmedCount.showNumber(newState.confirmed)
                    tvRecoveredCount.showNumber(newState.recovered)
                    tvDeceasedCount.showNumber(newState.deaths)

                    if (tvRecoveredCount.scanForBigTextAndWrapNextLine())
                        tvDeceasedDelta.setOrientation(LinearLayout.VERTICAL)
                    if (tvDeceasedCount.scanForBigTextAndWrapNextLine())
                        tvRecoveredDelta.setOrientation(LinearLayout.VERTICAL)

                    pieChart.loadData(
                        covidRes,
                        newState.active,
                        newState.recovered,
                        newState.deaths
                    )
                }

                binding.layoutStatList.root.visibility = if (newState.stats.isNullOrEmpty()) {
                    View.GONE
                } else {
                    statAdapter.submitList(newState.stats)
                    View.VISIBLE
                }
            }
        }
    }

    override fun onStateClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) = TODO()
    override fun onCountryClicked(covidStat: CovidStat) {
        navController.navigate(
            WorldFragmentDirections.actionWorldFragmentToCountryDataFragment(
                covidStat
            )
        )
    }
}