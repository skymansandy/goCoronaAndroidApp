package dev.skymansandy.gocorona.presentation.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import dev.skymansandy.gocorona.databinding.LayoutStatCardBinding
import dev.skymansandy.gocorona.databinding.LayoutStatListBinding
import dev.skymansandy.gocorona.presentation.choosecountry.ChooseCountryBottomSheet
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryItem
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import java.text.NumberFormat

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEvent, HomeViewModel>(),
    CovidStatClickListener {

    private val confirmedColor get() = ContextCompat.getColor(activity!!, R.color.color_confirmed)
    private val activeColor get() = ContextCompat.getColor(activity!!, R.color.color_active)
    private val recoveredColor get() = ContextCompat.getColor(activity!!, R.color.color_recovered)
    private val deceasedColor get() = ContextCompat.getColor(activity!!, R.color.color_deceased)

    private val statAdapter = CovidStatAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.layoutStatList.setup(statAdapter, "State/UT")

        binding.swipe.setOnRefreshListener {
            vm.refreshStats()
        }

        binding.tvPlace.setOnClickListener {
            ChooseCountryBottomSheet.getInstance(object : CountryClickListener {
                override fun onCountryClick(countryItem: CountryItem) {
                    vm.onUserEvent(HomeEvent.CountryClicked(countryItem.code))
                }
            }).show(childFragmentManager, "")
        }
    }

    override fun renderViewState(newState: HomeState) {
        binding.layoutLoading.visibility = View.GONE
        binding.layoutStats.visibility = View.GONE

        when (newState) {
            is HomeState.Loading -> {
                binding.swipe.isRefreshing = true
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is HomeState.State -> {
                binding.swipe.isRefreshing = false
                binding.layoutStats.visibility = View.VISIBLE

                with(binding) {
                    tvPlace.text = newState.placeName
                    tvLastUpdated.text = newState.lastUpdated
                    tvActiveCount.text =
                        NumberFormat.getInstance().format(newState.active.count.toInt())
                    statCardConfirmed.showStatCard(newState.confirmed, newState.growthTrendMaxScale)
                    statCardRecovered.showStatCard(newState.recovered, newState.growthTrendMaxScale)
                    statCardDeceased.showStatCard(newState.deceased, newState.growthTrendMaxScale)
                    layoutStatList.root.visibility = if (newState.stats.isNullOrEmpty()) {
                        View.GONE
                    } else {
                        statAdapter.submitList(newState.stats)
                        View.VISIBLE
                    }
                }
            }
        }
    }

    private fun LayoutStatCardBinding.showStatCard(
        stat: StatCard,
        growthTrendMaxScale: Float
    ) {
        snake.clear()
        snake.setMaxValue(growthTrendMaxScale)
        snake.visibility = if (growthTrendMaxScale >= 0) {
            stat.growthTrend.map { snake.addValue(it.toFloat()) }
            View.VISIBLE
        } else View.GONE

        tvCount.text = NumberFormat.getInstance().format(stat.count.toInt())
        tvDelta.visibility =
            if (stat.deltaCount.toInt() > 0) {
                tvDelta.text = NumberFormat.getInstance().format(stat.deltaCount.toInt())
                View.VISIBLE
            } else View.GONE

        activity?.let {
            when (this) {
                binding.statCardConfirmed -> {
                    tvTitle.text = "Confirmed"
                    tvCount.setTextColor(confirmedColor)
                    tvDelta.setTextColor(confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(confirmedColor)
                    tvDelta.compoundDrawables[2]?.setTint(confirmedColor)
                    snake.setStrokeColor(confirmedColor)
                }
                binding.statCardRecovered -> {
                    tvTitle.text = "Recovered"
                    tvCount.setTextColor(recoveredColor)
                    tvDelta.setTextColor(recoveredColor)
                    tvDelta.compoundDrawables[2]?.setTint(recoveredColor)
                    snake.setStrokeColor(recoveredColor)
                }
                binding.statCardDeceased -> {
                    tvTitle.text = "Deceased"
                    tvCount.setTextColor(deceasedColor)
                    tvDelta.setTextColor(deceasedColor)
                    tvDelta.compoundDrawables[2]?.setTint(deceasedColor)
                    snake.setStrokeColor(deceasedColor)
                }
            }
        }
    }

    private fun LayoutStatListBinding.setup(covidStatAdapter: CovidStatAdapter, title: String) {
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
}