package dev.skymansandy.base.domain.interactor

sealed class Result<T> {
    open fun get(): T? = null

    fun getOrThrow(): T = when (this) {
        is Success -> get()
        is Failure -> throw throwable
    }
}

data class Success<T>(val data: T) : Result<T>() {
    override fun get(): T = data
}

data class Failure<T>(val throwable: Throwable) : Result<T>()
