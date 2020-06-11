package dev.skymansandy.gocorona.presentation.news

import android.os.Bundle
import android.view.View
import dev.skymansandy.base.ui.base.BaseFragment
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.data.source.remote.GoCoronaApi
import dev.skymansandy.gocorona.databinding.FragmentHomeBinding
import dev.skymansandy.gocorona.databinding.FragmentNewsBinding
import javax.inject.Inject

class NewsFragment(override val layoutId: Int = R.layout.fragment_news) :
    BaseFragment<FragmentNewsBinding, NewsState, NewsEvent, NewsViewModel>() {

    @Inject
    lateinit var goCoronaApi: GoCoronaApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderViewState(newState: NewsState) {

    }

}