package dev.skymansandy.gocorona.presentation.settings

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.databinding.FragmentSettingsBinding
import javax.inject.Inject

class SettingsFragment(override val layoutId: Int = R.layout.fragment_settings) :
    BaseFragment<FragmentSettingsBinding, SettingsState, SettingsEvent, SettingsViewModel>() {

    @Inject
    lateinit var goCoronaApi: GoCoronaApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderViewState(newState: SettingsState) {

    }

}