package dev.skymansandy.gocorona.presentation.main

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.NavigationUI
import dev.skymansandy.base.ui.base.BaseActivity
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.ActivityMainBinding

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, Void, Void, MainViewModel>() {

    override val navControllerId = R.id.nav_host_main
    override val snackBarView: View
        get() = binding.snackBarContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
    }

    override fun renderViewState(newState: Void) {

    }
}