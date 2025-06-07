package com.niolasdev.randommouse.domain

sealed class CatResult<T> {
    data class Error<T>(val e: Throwable?): CatResult<T>()
    data class Success<T>(val data: T): CatResult<T>()
    data class InProgress<T>(val data: T? = null): CatResult<T>()
}

fun <In : Any, Out : Any> CatResult<In>.map(mapper: (In) -> Out): CatResult<Out> {
    return when (this) {
        is CatResult.InProgress -> CatResult.InProgress()
        is CatResult.Success -> CatResult.Success(mapper(data))
        is CatResult.Error -> CatResult.Error(e)
    }
}

internal fun <T : Any> Result<T>.toCatResult(): CatResult<T> {
    return when {
        isSuccess -> CatResult.Success(getOrThrow())
        isFailure -> CatResult.Error(exceptionOrNull())
        else -> error("Impossible branch")
    }
}
