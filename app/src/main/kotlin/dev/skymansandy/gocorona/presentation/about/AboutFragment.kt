package dev.skymansandy.gocorona.presentation.about

import android.os.Bundle
import android.view.View
import coil.api.load
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.base.util.general.AppUtil
import dev.skymansandy.base.util.general.TextUtil
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.databinding.FragmentAboutBinding
import dev.skymansandy.gocorona.presentation.about.settings.SettingsFragment


class AboutFragment(override val layoutId: Int = R.layout.fragment_about) :
    BaseFragment<FragmentAboutBinding, SettingsState, SettingsEvent, SettingsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAbout()
        setupCredits()
        setupSettingFragment()
    }

    private fun setupAbout() {
        binding.civDp.load(AppConstant.Url.DP_URL)
        binding.btnVisitBlog.setOnClickListener {
            AppUtil.launchUrl(activity, AppConstant.Url.BLOG_URL)
        }
    }

    private fun setupCredits() {
        binding.tvCreditIndiaData.text =
            String.format(
                "%s: %s",
                getString(R.string.india_data),
                TextUtil.getUrlClickableSpan(activity, AppConstant.Url.COVID_19_INDIA)
            )
        binding.tvCreditWorldData.text =
            String.format(
                "%s: %s",
                getString(R.string.world_data),
                TextUtil.getUrlClickableSpan(activity, AppConstant.Url.CORONA_LMAO_NINJA)
            )
        binding.tvCreditGraphLibrary.text =
            String.format(
                "%s: %s",
                getString(R.string.graph_library),
                TextUtil.getUrlClickableSpan(activity, AppConstant.Url.GRAPH_LIBRARY)
            )
        binding.tvCreditPieChartLibrary.text =
            String.format(
                "%s: %s",
                getString(R.string.piechart_library),
                TextUtil.getUrlClickableSpan(activity, AppConstant.Url.PIE_CHART_LIBRARY)
            )
    }

    private fun setupSettingFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, SettingsFragment())
            .commit()
    }

    override fun renderViewState(newState: SettingsState) {

    }
}