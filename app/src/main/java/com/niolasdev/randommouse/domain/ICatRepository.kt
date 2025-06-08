package com.niolasdev.randommouse.domain

import com.niolasdev.randommouse.data.Cat
import kotlinx.coroutines.flow.Flow

interface ICatRepository {

    fun getCats(): Flow<CatResult<List<Cat>>>
}