package dev.skymansandy.gocorona.presentation.home

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStat
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatAdapter
import dev.skymansandy.gocorona.presentation.home.adapter.CovidStatClickListener
import javax.inject.Inject

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEvent, HomeViewModel>(),
    CovidStatClickListener {

    @Inject
    lateinit var goCoronaApi: GoCoronaApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.fetchCountryCases()

        val adapter = CovidStatAdapter(this)
        val list = arrayListOf<CovidStat>()
        for (i in 1..38) {
            list += CovidStat()
        }
        binding.rvStats.adapter = adapter
        adapter.submitList(list)
    }

    override fun renderViewState(newState: HomeState) {

    }

}