package dev.skymansandy.gocorona.presentation.countrydata

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentCountryDataBinding
import dev.skymansandy.gocorona.presentation.home.adapter.showDelta
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.setOrientation
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber

class CountryDataFragment(override val layoutId: Int = R.layout.fragment_country_data) :
    BaseFragment<FragmentCountryDataBinding, CountryDataState, CountryDataEvent, CountryDataViewModel>() {

    private val covidRes by lazy { CovidResImpl(requireContext()) }
    private val args by navArgs<CountryDataFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.getDistrictData(args.country.code)

        binding.tvPlace.text = args.country.name
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
                    String.format("%s %s", getString(R.string.last_synced), newState.lastUpdated)
                with(binding.statsNonIndia) {
                    tvActiveCount.showNumber(newState.active)
                    tvConfirmedCount.showNumber(newState.confirmed)
                    tvRecoveredCount.showNumber(newState.recovered)
                    tvDeceasedCount.showNumber(newState.deaths)

                    if (tvRecoveredCount.scanForBigTextAndWrapNextLine())
                        tvDeceasedDelta.setOrientation(LinearLayout.VERTICAL)
                    if (tvDeceasedCount.scanForBigTextAndWrapNextLine())
                        tvRecoveredDelta.setOrientation(LinearLayout.VERTICAL)

                    showDelta(covidRes, tvConfirmedDelta, newState.confirmedToday)
                    showDelta(covidRes, tvRecoveredDelta, newState.recoveredToday)
                    showDelta(covidRes, tvDeceasedDelta, newState.deathsToday)
                    pieChart.loadData(
                        covidRes,
                        newState.active,
                        newState.recovered,
                        newState.deaths
                    )
                }
            }
        }
    }
}