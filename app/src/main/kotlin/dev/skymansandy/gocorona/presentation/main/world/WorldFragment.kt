package dev.skymansandy.gocorona.presentation.main.world

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.paging.PagedList
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentWorldBinding
import dev.skymansandy.gocorona.presentation.main.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.main.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.main.india.adapter.*
import dev.skymansandy.gocorona.tools.coviduitools.covidcolor.CovidResImpl
import dev.skymansandy.gocorona.tools.coviduitools.extension.loadData
import dev.skymansandy.gocorona.tools.coviduitools.extension.scanForBigTextAndWrapNextLine
import dev.skymansandy.gocorona.tools.coviduitools.extension.setOrientation
import dev.skymansandy.gocorona.tools.coviduitools.extension.showNumber
import javax.inject.Inject

class WorldFragment(override val layoutId: Int = R.layout.fragment_world) :
    BaseFragment<FragmentWorldBinding, WorldState, WorldEvent, WorldViewModel>(),
    CovidStatClickListener {

    private val covidRes by lazy { CovidResImpl(requireContext()) }
    private val statAdapter = CovidStatListAdapter(this, CovidStatListType.COUNTRY)

    @Inject
    lateinit var pageConfig: PagedList.Config

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        when (newState) {
            is WorldState.Loading -> {
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is WorldState.WorldDetails -> {
                binding.statsWorld.layoutStats.visibility = View.VISIBLE
                binding.layoutStatList.root.visibility = View.VISIBLE
                binding.tvLastUpdated.text =
                    String.format("%s %s", getString(R.string.last_synced), newState.lastUpdated)
                binding.layoutStatList.setup(covidRes, statAdapter, getString(R.string.country))
                with(binding.statsWorld) {
                    tvActiveCount.showNumber(newState.active)
                    tvConfirmedCount.showNumber(newState.confirmed)
                    tvRecoveredCount.showNumber(newState.recovered)
                    tvDeceasedCount.showNumber(newState.deceased)

                    if (tvRecoveredCount.scanForBigTextAndWrapNextLine())
                        tvDeceasedDelta.setOrientation(LinearLayout.VERTICAL)
                    if (tvDeceasedCount.scanForBigTextAndWrapNextLine())
                        tvRecoveredDelta.setOrientation(LinearLayout.VERTICAL)

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