package dev.skymansandy.gocorona.presentation.main

sealed class MainState {
    object Loading : MainState()
    object Loaded : MainState()
    data class Error(val error: String?) : MainState()
}