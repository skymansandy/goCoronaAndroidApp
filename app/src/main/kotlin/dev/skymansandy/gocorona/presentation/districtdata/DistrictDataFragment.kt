package dev.skymansandy.gocorona.presentation.districtdata

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentDistrictDataBinding
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

class DistrictDataFragment(override val layoutId: Int = R.layout.fragment_district_data) :
    BaseFragment<FragmentDistrictDataBinding, DistrictDataState, DistrictDataEvent, DistrictDataViewModel>() {

    private val confirmedColor get() = ContextCompat.getColor(activity!!, R.color.color_confirmed)
    private val activeColor get() = ContextCompat.getColor(activity!!, R.color.color_active)
    private val recoveredColor get() = ContextCompat.getColor(activity!!, R.color.color_recovered)
    private val deceasedColor get() = ContextCompat.getColor(activity!!, R.color.color_deceased)

    private val args by navArgs<DistrictDataFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPlace.text = args.district.name
        vm.getDistrictData(args.district.code)
        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }
    }

    override fun renderViewState(newState: DistrictDataState) {
        binding.swipe.isRefreshing = false
        binding.statsNonIndia.root.visibility = View.GONE

        when (newState) {
            is DistrictDataState.Loading -> {
                binding.swipe.isRefreshing = true
            }
            is DistrictDataState.DistrictStats -> {
                binding.swipe.isRefreshing = false
                binding.statsNonIndia.root.visibility = View.VISIBLE

                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text = newState.lastUpdated
                with(binding.statsNonIndia) {
                    tvActiveCount.text =
                        NumberFormat.getInstance().format(newState.active.toInt())
                    tvConfirmedCount.text =
                        NumberFormat.getInstance().format(newState.confirmed.toInt())
                    tvRecoveredCount.text =
                        NumberFormat.getInstance().format(newState.recovered.toInt())
                    tvDeceasedCount.text =
                        NumberFormat.getInstance().format(newState.deaths.toInt())
                    pieChart.loadData(
                        newState.active.toInt(),
                        newState.recovered.toInt(),
                        newState.deaths.toInt()
                    )
                }
            }
        }
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
}