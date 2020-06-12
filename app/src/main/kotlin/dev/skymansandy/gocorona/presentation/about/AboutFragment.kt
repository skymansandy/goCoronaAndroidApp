package dev.skymansandy.gocorona.presentation.about

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentAboutBinding
import dev.skymansandy.gocorona.presentation.about.settings.SettingsFragment

class AboutFragment(override val layoutId: Int = R.layout.fragment_about) :
    BaseFragment<FragmentAboutBinding, SettingsState, SettingsEvent, SettingsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchFragment()
    }

    override fun renderViewState(newState: SettingsState) {

    }

    private fun setupSearchFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, SettingsFragment())
            .commit()
    }
}