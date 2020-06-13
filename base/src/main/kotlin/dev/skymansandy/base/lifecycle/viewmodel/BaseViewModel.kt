package dev.skymansandy.base.lifecycle.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.skymansandy.base.exception.NoObserverAttachedException
import dev.skymansandy.base.lifecycle.event.SingleLiveEvent
import timber.log.Timber

abstract class BaseViewModel<STATE, EVENT>
    : ViewModel(), BaseViewModelContract<EVENT> {

    /**
     * MediatorLiveData for some internal processing of
     * states and effects of the UI state
     */
    private val _mediator = MediatorLiveData<Void>()
    val mediator: MediatorLiveData<Void>
        get() = _mediator

    /**
     * View States
     */
    fun viewStates(): LiveData<STATE> = _viewStates
    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Timber.d("setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }

    /**
     * Action: Show Toast
     */
    private val actionShowToast = SingleLiveEvent<String?>()
    fun actionToast() = actionShowToast
    fun showToast(message: String?) {
        actionShowToast.value = message
    }

    /**
     * Action: Show SnackBar
     */
    private val actionSnackBar = SingleLiveEvent<String?>()
    fun actionSnackBar() = actionSnackBar
    fun showSnackBar(message: String?) {
        actionSnackBar.value = message
    }

    @CallSuper
    override fun onUserEvent(viewEvent: EVENT) {
        if (!viewStates().hasObservers()) {
            throw NoObserverAttachedException("No observer attached. In case of custom View \"startObserving()\" function needs to be called manually.")
        }
        Timber.d("processing viewEvent: $viewEvent")
    }

    override fun onCleared() {
        super.onCleared()
    }
}
