package dev.skymansandy.gocorona.presentation.health

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.databinding.FragmentHealthBinding
import javax.inject.Inject

class HealthFragment(override val layoutId: Int = R.layout.fragment_health) :
    BaseFragment<FragmentHealthBinding, HealthState, HealthEvent, HealthViewModel>() {

    @Inject
    lateinit var goCoronaApi: GoCoronaApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderViewState(newState: HealthState) {

    }

}