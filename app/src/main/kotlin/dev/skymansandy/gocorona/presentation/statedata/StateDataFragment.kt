package dev.skymansandy.gocorona.presentation.statedata

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentStateDataBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import dev.skymansandy.gocorona.presentation.home.adapter.showDelta
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

class StateDataFragment(override val layoutId: Int = R.layout.fragment_state_data) :
    BaseFragment<FragmentStateDataBinding, StateDataState, StateDataEvent, StateDataViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(activity!!) }
    private val statAdapter = CovidStatAdapter(this, true)

    private val args by navArgs<StateDataFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPlace.text = args.state.name
        vm.getStateData(args.state.code)
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
                binding.swipe.isRefreshing = false
                binding.statsNonIndia.root.visibility = View.VISIBLE
                binding.layoutStatList.root.visibility = View.VISIBLE

                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text = "Last synced at ${newState.lastUpdated}"
                binding.layoutStatList.setup(statAdapter, "District")
                with(binding.statsNonIndia) {
                    tvActiveCount.text =
                        NumberFormat.getInstance().format(newState.active.toInt())
                    tvConfirmedCount.text =
                        NumberFormat.getInstance().format(newState.confirmed.toInt())
                    tvRecoveredCount.text =
                        NumberFormat.getInstance().format(newState.recovered.toInt())
                    tvDeceasedCount.text =
                        NumberFormat.getInstance().format(newState.deaths.toInt())

                    tvConfirmedDelta.visibility = View.VISIBLE
                    showDelta(covidRes, tvConfirmedDelta, newState.confirmedToday.toInt())
                    tvRecoveredDelta.visibility = View.VISIBLE
                    showDelta(covidRes, tvRecoveredDelta, newState.recoveredToday.toInt())
                    tvDeceasedDelta.visibility = View.VISIBLE
                    showDelta(covidRes, tvDeceasedDelta, newState.deathsToday.toInt())

                    pieChart.loadData(
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

    //TODO optimize
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

    private fun PieChart.loadData(active: Int, recovered: Int, deceased: Int) {
        clearChart()
        addPieSlice(
            PieModel("Active", active.toFloat(), covidRes.activeColor)
        )
        addPieSlice(
            PieModel("Recovered", recovered.toFloat(), covidRes.recoveredColor)
        )
        addPieSlice(
            PieModel("Deceased", deceased.toFloat(), covidRes.deceasedColor)
        )
        startAnimation()
    }
}