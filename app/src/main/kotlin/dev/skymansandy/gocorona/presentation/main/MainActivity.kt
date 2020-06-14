package dev.skymansandy.gocorona.presentation.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.ui.NavigationUI
import dev.skymansandy.base.extension.isEmptyBackStack
import dev.skymansandy.base.ui.base.BaseActivity
import dev.skymansandy.gocorona.R
import dev.skymansandy.gocorona.databinding.ActivityMainBinding

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, Void, Void, MainViewModel>() {

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
            vm.refreshStats()
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

    override fun renderViewState(newState: Void) {}
}