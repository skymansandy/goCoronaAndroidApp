package dev.skymansandy.gocorona.presentation.main

sealed class MainState {
    object Loading : MainState()
    object Loaded : MainState()
}