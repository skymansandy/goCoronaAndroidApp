package dev.skymansandy.base.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dev.skymansandy.base.lifecycle.viewmodel.BaseViewModel
import dev.skymansandy.base.util.ui.KeyboardUtil
import dev.skymansandy.base.util.ui.SnackBarUtil
import dev.skymansandy.base.util.ui.ToastUtil
import timber.log.Timber
import javax.inject.Inject

abstract class BaseDialogFragment<T : ViewDataBinding,
        STATE, EVENT, VM : BaseViewModel<STATE, EVENT>> :
    DialogFragment(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    var baseActivity: BaseActivity<*, *, *, *>? = null
        private set

    private lateinit var mRootView: View

    @get:LayoutRes
    abstract val layoutId: Int

    @Inject
    lateinit var vm: VM

    lateinit var binding: T
        private set


    private val viewStateObserver = Observer<STATE> {
        Timber.d("observed viewState : $it")
        renderViewState(it)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity<*, *, *, *>) {
            this.baseActivity = context
            KeyboardUtil.hideKeyboard(context)
        }
    }

    //TODO add configurable option
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = binding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(vm) {
            viewStates().observe(viewLifecycleOwner, viewStateObserver)
        }
        vm.mediator.observe(viewLifecycleOwner, Observer {})
        setupActions()
    }

    private fun setupActions() {
        vm.actionToast().observe(viewLifecycleOwner, Observer {
            it?.let { ToastUtil.showShortToast(context, it) }
        })

        vm.actionSnackBar().observe(viewLifecycleOwner, Observer {
            it?.let {
                (activity as? BaseActivity<*, *, *, *>)?.run {
                    SnackBarUtil.showShortSnack(snackBarView, it)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    abstract fun renderViewState(newState: STATE)
}