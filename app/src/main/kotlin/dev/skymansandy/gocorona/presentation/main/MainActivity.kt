package dev.skymansandy.gocorona.presentation.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.ui.NavigationUI
import dev.skymansandy.base.extension.isEmptyBackStack
import dev.skymansandy.base.ui.base.BaseActivity
import dev.skymansandy.base.util.general.NetworkUtil
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.ActivityMainBinding

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainState, Void, MainViewModel>() {

    override val navControllerId = R.id.nav_host_main
    override val snackBarView: View
        get() = binding.snackBarContainer

    private val navHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_main)!!

    private var pressedBackOnce: Boolean = false
    private val backPressHandler = Handler()
    private val backPressRunnable = {
        pressedBackOnce = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationUI.setupWithNavController(binding.bottomNavView, navController)
        binding.swipe.setOnRefreshListener {
            if (NetworkUtil.checkInternetConnectivity(this)) {
                vm.refreshStats()
            } else {
                vm.showSnackBar(getString(R.string.check_internet_connection))
            }
            binding.swipe.isRefreshing = false
        }
    }

    override fun onBackPressed() {
        if (navHostFragment.isEmptyBackStack()) {
            if (pressedBackOnce) finish()
            else {
                pressedBackOnce = true
                vm.showToast(getString(R.string.press_back_again_to_exit))
                backPressHandler.postDelayed(backPressRunnable, 3000)
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun renderViewState(newState: MainState) {
        when (newState) {
            is MainState.Loading -> {
                vm.showSnackBar(getString(R.string.syncing_latest_data))
            }
            is MainState.Loaded -> {
                vm.showSnackBar(getString(R.string.synced_successfully))
            }
            is MainState.Error -> {
                vm.showSnackBar(getString(R.string.couldnt_sync_data))
            }
        }
    }
}