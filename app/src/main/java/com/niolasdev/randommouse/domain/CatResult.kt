package com.niolasdev.randommouse.domain

sealed class CatResult<T>(open val data: T? = null) {
    data class Error<T>(override val data: T? = null, val e: Throwable?): CatResult<T>()
    data class Success<T>(override val data: T): CatResult<T>()
    data class InProgress<T>(override val data: T? = null): CatResult<T>()
}

fun <In : Any, Out : Any> CatResult<In>.map(mapper: (In) -> Out): CatResult<Out> {
    return when (this) {
        is CatResult.InProgress -> CatResult.InProgress()
        is CatResult.Success -> CatResult.Success(mapper(data))
        is CatResult.Error -> CatResult.Error(e = e)
    }
}

internal fun <T : Any> Result<T>.toCatResult(): CatResult<T> {
    return when {
        isSuccess -> CatResult.Success(getOrThrow())
        isFailure -> CatResult.Error(e = exceptionOrNull())
        else -> error("Impossible branch")
    }
}
