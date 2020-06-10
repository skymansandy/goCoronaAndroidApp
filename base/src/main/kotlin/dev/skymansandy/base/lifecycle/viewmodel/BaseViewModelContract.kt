package dev.skymansandy.base.lifecycle.viewmodel

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface BaseViewModelContract<EVENT> {
    fun onUserEvent(viewEvent: EVENT)
}