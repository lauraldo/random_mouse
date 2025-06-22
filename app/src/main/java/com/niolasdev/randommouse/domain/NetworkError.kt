package com.niolasdev.randommouse.domain

sealed class NetworkError : Exception() {
    data class NoConnection(override val message: String = "No internet connection") : NetworkError()
    data class ServerError(override val message: String = "Server error") : NetworkError()
    data class UnknownError(override val message: String = "Unknown error occurred") : NetworkError()
    data class TimeoutError(override val message: String = "Request timeout") : NetworkError()
} 