package dev.skymansandy.gocorona.presentation.world

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentWorldBinding
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber

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

        binding.ivSearchCountries.setOnClickListener {
            ChooseCountryBottomSheet.getInstance(object : CountryClickListener {
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
                    String.format("%s %s", getString(R.string.last_synced_at), newState.lastUpdated)
                with(binding.statsWorld) {
                    tvActiveCount.showNumber(newState.active.count.toInt())
                    tvConfirmedCount.showNumber(newState.confirmed.count.toInt())
                    tvRecoveredCount.showNumber(newState.recovered.count.toInt())
                    tvDeceasedCount.showNumber(newState.deceased.count.toInt())

                    tvRecoveredCount.scanForBigTextAndWrapNextLine()
                    tvDeceasedCount.scanForBigTextAndWrapNextLine()

                    pieChart.loadData(
                        covidRes,
                        newState.active.count.toInt(),
                        newState.recovered.count.toInt(),
                        newState.deceased.count.toInt()
                    )
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