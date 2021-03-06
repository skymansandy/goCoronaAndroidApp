package dev.skymansandy.gocorona.presentation.main.india.state

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentStateBinding
import dev.skymansandy.gocorona.presentation.main.india.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.setOrientation
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber
import javax.inject.Inject

class StateFragment(override val layoutId: Int = R.layout.fragment_state) :
    BaseFragment<FragmentStateBinding, StateDataState, StateDataEvent, StateDataViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(requireContext()) }
    private val statAdapter = CovidStatListAdapter(this, CovidStatListType.DISTRICT)
    private val args by navArgs<StateFragmentArgs>()

    @Inject
    lateinit var pageConfig: PagedList.Config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getStateData(args.state.code)

        binding.tvPlace.text = args.state.name
    }

    override fun renderViewState(newState: StateDataState) {
        binding.statsNonIndia.root.visibility = View.GONE
        binding.layoutStatList.root.visibility = View.GONE

        when (newState) {
            is StateDataState.Loading -> {
                //TODO
            }
            is StateDataState.StateStats -> {
                binding.statsNonIndia.root.visibility = View.VISIBLE
                binding.layoutStatList.root.visibility = View.VISIBLE
                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced), newState.lastUpdated)
                binding.layoutStatList.setup(covidRes, statAdapter, getString(R.string.district))

                with(binding.statsNonIndia) {
                    tvActiveCount.showNumber(newState.active)
                    tvConfirmedCount.showNumber(newState.confirmed)
                    tvRecoveredCount.showNumber(newState.recovered)
                    tvDeceasedCount.showNumber(newState.deceased)

                    if (tvRecoveredCount.scanForBigTextAndWrapNextLine())
                        tvDeceasedDelta.setOrientation(LinearLayout.VERTICAL)
                    if (tvDeceasedCount.scanForBigTextAndWrapNextLine())
                        tvRecoveredDelta.setOrientation(LinearLayout.VERTICAL)

                    showDelta(covidRes, tvConfirmedDelta, newState.confirmedToday)
                    showDelta(covidRes, tvRecoveredDelta, newState.recoveredToday)
                    showDelta(covidRes, tvDeceasedDelta, newState.deceasedToday)
                    pieChart.loadData(
                        covidRes,
                        newState.active,
                        newState.recovered,
                        newState.deceased
                    )
                }
                binding.layoutStatList.root.visibility = if (newState.stats.isNullOrEmpty()) {
                    View.GONE
                } else {
                    val pagedStrings = getPagedList(newState.stats, pageConfig)
                    statAdapter.submitList(pagedStrings)
                    View.VISIBLE
                }
            }
        }
    }

    override fun onCountryClicked(covidStat: CovidStat) = TODO()
    override fun onStateClicked(covidStat: CovidStat) = TODO()
    override fun onDistrictClicked(covidStat: CovidStat) {
        navController.navigate(
            StateFragmentDirections.actionStateDataFragmentToDistrictDataFragment(
                covidStat
            )
        )
    }
}