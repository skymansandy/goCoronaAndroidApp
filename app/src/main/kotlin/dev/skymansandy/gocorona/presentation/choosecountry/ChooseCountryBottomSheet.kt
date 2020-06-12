package dev.skymansandy.gocorona.presentation.choosecountry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import dev.skymansandy.base.ui.base.BaseDialogFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.databinding.FragmentChooseCountryBinding
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryClickListener
import dev.skymansandy.gocorona.presentation.choosecountry.adapter.CountryItemAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat


class ChooseCountryBottomSheet(override val layoutId: Int = R.layout.fragment_choose_country) :
    BaseDialogFragment<FragmentChooseCountryBinding, ChooseCountryState, ChooseCountryEvent, ChooseCountryViewModel>(),
    CountryClickListener {

    private lateinit var countryClickListener: CountryClickListener
    private val countryAdapter = CountryItemAdapter(this)

    companion object {
        fun getInstance(countryClickListener: CountryClickListener): ChooseCountryBottomSheet {
            return ChooseCountryBottomSheet().apply {
                this.countryClickListener = countryClickListener
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWorldRow()
        binding.ivExit.setOnClickListener { dismiss() }
        binding.rvCountries.adapter = countryAdapter
        binding.etCountrySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                vm.onUserEvent(ChooseCountryEvent.SearchQuery(s.toString()))
            }
        })
    }

    private fun setupWorldRow() {
        binding.layoutWorldwide.setOnClickListener {
            onCountryClick(
                CovidStat(
                    code = AppConstant.WORLDWIDE,
                    name = "Worldwide"
                )
            )
        }
    }

    override fun renderViewState(newState: ChooseCountryState) {
        binding.layoutCountries.visibility = View.GONE
        binding.layoutLoading.visibility = View.GONE

        when (newState) {
            is ChooseCountryState.Loading -> {
                binding.layoutLoading.visibility = View.VISIBLE
            }
            is ChooseCountryState.State -> {
                binding.layoutCountries.visibility = View.VISIBLE
                countryAdapter.submitList(newState.countries)
            }
        }
    }

    override fun onCountryClick(covidStat: CovidStat) {
        countryClickListener.onCountryClick(covidStat)
        dismiss()
    }
}