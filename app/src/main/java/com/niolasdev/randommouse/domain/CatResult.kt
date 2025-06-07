package com.niolasdev.randommouse.domain

sealed class CatResult<T> {
    data class Error<T>(val e: Throwable?): CatResult<T>()
    data class Success<T>(val data: T): CatResult<T>()
}