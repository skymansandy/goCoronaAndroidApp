package dev.skymansandy.gocorona.presentation.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.content.ContextCompat
import coil.api.load
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.base.util.general.AppUtil
import dev.skymansandy.base.util.general.TextUtil
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.constant.AppConstant.Url
import dev.skymansandy.gocorona.databinding.FragmentAboutBinding
import dev.skymansandy.gocorona.presentation.about.settings.SettingsFragment


class AboutFragment(override val layoutId: Int = R.layout.fragment_about) :
    BaseFragment<FragmentAboutBinding, SettingsState, SettingsEvent, SettingsViewModel>() {

    private val colorPrimary
        get() = context?.let { ContextCompat.getColor(it, R.color.color_primary) } ?: 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAbout()
        setupCredits()
        setupSettingFragment()
    }

    private fun setupAbout() {
        binding.civDp.load(R.drawable.sandesh)
        binding.btnVisitBlog.setOnClickListener {
            activity?.let {
                AppUtil.launchInChromeOrDefault(
                    activity,
                    Url.BLOG_URL,
                    ContextCompat.getColor(it, R.color.color_primary)
                )
            }
        }
    }

    private fun setupCredits() {
        with(binding) {
            tvCreditIndiaData.movementMethod = LinkMovementMethod.getInstance()
            tvCreditWorldData.movementMethod = LinkMovementMethod.getInstance()
            tvCreditGraphLibrary.movementMethod = LinkMovementMethod.getInstance()
            tvCreditPieChartLibrary.movementMethod = LinkMovementMethod.getInstance()
            activity?.let {
                tvCreditIndiaData.text = TextUtil.getUrlClickableSpan(it, Url.COVID_19_INDIA)
                tvCreditWorldData.text = TextUtil.getUrlClickableSpan(it, Url.CORONA_LMAO_NINJA)
                tvCreditGraphLibrary.text = TextUtil.getUrlClickableSpan(it, Url.GRAPH_LIBRARY)
                tvCreditPieChartLibrary.text =
                    TextUtil.getUrlClickableSpan(it, Url.PIE_CHART_LIBRARY)
            }
        }
    }

    private fun setupSettingFragment() {
        childFragmentManager
            .beginTransaction()
            .replace(binding.container.id, SettingsFragment())
            .commit()
    }

    override fun renderViewState(newState: SettingsState) {}
}