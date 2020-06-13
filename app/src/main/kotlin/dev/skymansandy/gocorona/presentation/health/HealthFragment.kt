package dev.skymansandy.gocorona.presentation.health

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.base.util.general.AppUtil
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.constant.AppConstant
import dev.skymansandy.gocorona.databinding.FragmentHealthBinding
import dev.skymansandy.gocorona.presentation.health.adapter.ImageClickListener
import dev.skymansandy.gocorona.presentation.health.adapter.ImageItem
import dev.skymansandy.gocorona.presentation.health.adapter.ImageItemAdapter

class HealthFragment(override val layoutId: Int = R.layout.fragment_health) :
    BaseFragment<FragmentHealthBinding, HealthState, HealthEvent, HealthViewModel>(),
    ImageClickListener {

    private val colorPrimary
        get() = activity?.let {
            ContextCompat.getColor(it, R.color.color_primary)
        } ?: 0

    private val imageAdapter = ImageItemAdapter(this).apply {
        submitList(healthTipsArray)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvWhoImages.adapter = imageAdapter
        binding.cvSymptoms.setOnClickListener {
            activity?.let {
                AppUtil.launchUrlInChromeTab(it, AppConstant.Url.SYMPTOMS_URL, colorPrimary)
            }
        }
        binding.cvPreventiveMeasures.setOnClickListener {
            activity?.let {
                AppUtil.launchUrlInChromeTab(
                    it,
                    AppConstant.Url.PREVENTIVE_MEASURE_URL,
                    colorPrimary
                )
            }
        }
        binding.cvMythsDebunked.setOnClickListener {

        }
    }

    override fun renderViewState(newState: HealthState) {

    }

    override fun onImageClick(imageItem: ImageItem) {

    }

}