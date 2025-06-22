package com.niolasdev.randommouse.domain

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    fun isOnline(): Boolean
    fun observeNetworkState(): Flow<Boolean>
} 