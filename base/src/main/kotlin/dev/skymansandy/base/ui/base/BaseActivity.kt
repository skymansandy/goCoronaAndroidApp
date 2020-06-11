package dev.skymansandy.base.ui.base

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import dagger.android.support.DaggerAppCompatActivity
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.base.util.ui.KeyboardUtil
import dev.skymansandy.base.util.ui.SnackBarUtil
import dev.skymansandy.base.util.ui.ToastUtil
import timber.log.Timber
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding,
        STATE, EVENT, VM : BaseViewModel<STATE, EVENT>>
    : DaggerAppCompatActivity() {

    @get:LayoutRes
    abstract val layoutId: Int

    @get:IdRes
    abstract val navControllerId: Int

    abstract val snackBarView: View

    val navController: NavController
        get() = findNavController(this, navControllerId)

    lateinit var binding: T

    @Inject
    lateinit var vm: VM

    private val viewStateObserver = Observer<STATE> {
        Timber.d("observed viewState : $it")
        renderViewState(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(vm) {
            viewStates().observe(this@BaseActivity, viewStateObserver)
        }
        vm.mediator.observe(this@BaseActivity, Observer {})
        setupActions()
    }

    private fun setupActions() {
        vm.actionToast().observe(this, Observer {
            it?.let { ToastUtil.showShortToast(applicationContext, it) }
        })

        vm.actionSnackBar().observe(this, Observer {
            it?.let { SnackBarUtil.showShortSnack(snackBarView, it) }
        })
    }

    override fun onResume() {
        super.onResume()
        KeyboardUtil.hideKeyboard(this)
    }

    override fun onBackPressed() {
        KeyboardUtil.hideKeyboard(this)
        super.onBackPressed()
    }

    abstract fun renderViewState(newState: STATE)
}