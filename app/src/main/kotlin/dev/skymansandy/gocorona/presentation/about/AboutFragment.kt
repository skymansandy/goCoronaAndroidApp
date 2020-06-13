package dev.skymansandy.gocorona.presentation.about

import android.os.Bundle
import android.view.View
import coil.api.load
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.FragmentAboutBinding
import dev.skymansandy.gocorona.presentation.about.settings.SettingsFragment

class AboutFragment(override val layoutId: Int = R.layout.fragment_about) :
    BaseFragment<FragmentAboutBinding, SettingsState, SettingsEvent, SettingsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDp()
        setupCredits()
        setupSettingFragment()
    }

    private fun setupDp() {
        binding.civDp.load("https://skymansandy.dev/wp-content/uploads/2020/06/imageedit_2_2677608069.png")
    }

    private fun setupCredits() {
    }

    override fun renderViewState(newState: SettingsState) {

    }

    private fun setupSettingFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, SettingsFragment())
            .commit()
    }
}