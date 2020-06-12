package dev.skymansandy.gocorona.presentation.districtdata

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentDistrictDataBinding
import dev.skymansandy.gocorona.presentation.countrydata.CountryDataEvent
import dev.skymansandy.gocorona.presentation.countrydata.CountryDataState
import dev.skymansandy.gocorona.presentation.countrydata.CountryDataViewModel
import dev.skymansandy.gocorona.presentation.home.adapter.showDelta
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber

class DistrictDataFragment(override val layoutId: Int = R.layout.fragment_district_data) :
    BaseFragment<FragmentDistrictDataBinding, CountryDataState, CountryDataEvent, CountryDataViewModel>() {

    private val covidRes by lazy { CovidResImpl(activity!!) }
    private val args by navArgs<DistrictDataFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getDistrictData(args.district.code)

        binding.tvPlace.text = args.district.name
        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }
    }

    override fun renderViewState(newState: CountryDataState) {
        binding.swipe.isRefreshing = false
        binding.statsNonIndia.root.visibility = View.GONE

        when (newState) {
            is CountryDataState.Loading -> {
                binding.swipe.isRefreshing = true
            }
            is CountryDataState.CountryStats -> {
                binding.statsNonIndia.root.visibility = View.VISIBLE
                binding.tvPlace.text = newState.placeName
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced_at), newState.lastUpdated)
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
            }
        }
    }
}