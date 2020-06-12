package dev.skymansandy.gocorona.presentation.statedata

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentStateDataBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat
import kotlin.math.absoluteValue

class StateDataFragment(override val layoutId: Int = R.layout.fragment_state_data) :
    BaseFragment<FragmentStateDataBinding, StateDataState, StateDataEvent, StateDataViewModel>(),
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

                    tvConfirmedCount.visibility = View.VISIBLE
                    showDelta(tvConfirmedCount, newState.confirmedToday.toInt())
                    tvRecoveredCount.visibility = View.VISIBLE
                    showDelta(tvRecoveredCount, newState.recoveredToday.toInt())
                    tvDeceasedCount.visibility = View.VISIBLE
                    showDelta(tvDeceasedCount, newState.deathsToday.toInt())

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
        statListHeader.tvActive.setTextColor(activeColor)
        statListHeader.tvConfirmed.setTextColor(confirmedColor)
        statListHeader.tvRecovered.setTextColor(recoveredColor)
        statListHeader.tvDeceased.setTextColor(deceasedColor)
    }

    private fun PieChart.loadData(active: Int, recovered: Int, deceased: Int) {
        clearChart()
        addPieSlice(
            PieModel("Active", active.toFloat(), activeColor)
        )
        addPieSlice(
            PieModel("Recovered", recovered.toFloat(), recoveredColor)
        )
        addPieSlice(
            PieModel("Deceased", deceased.toFloat(), deceasedColor)
        )
        startAnimation()
    }

    private fun showDelta(textView: TextView, delta: Int) {
        textView.visibility =
            if (delta != 0) {
                textView.text =
                    NumberFormat.getInstance().format(delta.absoluteValue)
                when {
                    delta < 0 ->
                        textView.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            downDrawable,
                            null
                        )
                    else ->
                        textView.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            upDrawable,
                            null
                        )
                }
                View.VISIBLE
            } else View.GONE
    }
}