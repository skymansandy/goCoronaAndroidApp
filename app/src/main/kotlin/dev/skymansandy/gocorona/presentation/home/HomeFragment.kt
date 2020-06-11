package dev.skymansandy.gocorona.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.data.source.remote.countrywise.CountryWiseDataResponse
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HomeFragment(override val layoutId: Int = R.layout.fragment_home) :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEvent, HomeViewModel>() {

    @Inject
    lateinit var goCoronaApi: GoCoronaApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.fetchCountryCases()
    }

    override fun renderViewState(newState: HomeState) {

    }

}