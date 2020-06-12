package dev.skymansandy.gocorona.presentation.statedata

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentStateDataBinding
import dev.skymansandy.gocorona.presentation.home.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber

class StateDataFragment(override val layoutId: Int = R.layout.fragment_state_data) :
    BaseFragment<FragmentStateDataBinding, StateDataState, StateDataEvent, StateDataViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(activity!!) }
    private val statAdapter = CovidStatListAdapter(this, CovidStatListType.DISTRICT)
    private val args by navArgs<StateDataFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getStateData(args.state.code)

        binding.tvPlace.text = args.state.name
        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }
    }

    override fun renderViewState(newState: StateDataState) {
        binding.swipe.isRefreshing = false
        binding.statsNonIndia.root.visibility = View.GONE
        binding.layoutStatList.root.visibility = View.GONE

        when (newState) {
            is StateDataState.Loading -> {
                binding.swipe.isRefreshing = true
            }
            is StateDataState.StateStats -> {
                binding.statsNonIndia.root.visibility = View.VISIBLE
                binding.layoutStatList.root.visibility = View.VISIBLE
                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced_at), newState.lastUpdated)
                binding.layoutStatList.setup(covidRes, statAdapter, getString(R.string.district))

                with(binding.statsNonIndia) {
                    tvActiveCount.showNumber(newState.active.toInt())
                    tvConfirmedCount.showNumber(newState.confirmed.toInt())
                    tvRecoveredCount.showNumber(newState.recovered.toInt())
                    tvDeceasedCount.showNumber(newState.deaths.toInt())

                    tvRecoveredCount.scanForBigTextAndWrapNextLine()
                    tvDeceasedCount.scanForBigTextAndWrapNextLine()

                    showDelta(covidRes, tvConfirmedDelta, newState.confirmedToday.toInt())
                    showDelta(covidRes, tvRecoveredDelta, newState.recoveredToday.toInt())
                    showDelta(covidRes, tvDeceasedDelta, newState.deathsToday.toInt())
                    pieChart.loadData(
                        covidRes,
                        newState.active.toInt(),
                        newState.recovered.toInt(),
                        newState.deaths.toInt()
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

    override fun onCountryClicked(covidStat: CovidStat) = TODO()
    override fun onStateClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) {
        navController.navigate(
            StateDataFragmentDirections.actionStateDataFragmentToDistrictDataFragment(
                covidStat
            )
        )
    }
}