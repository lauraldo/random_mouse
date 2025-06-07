package com.niolasdev.randommouse.domain

import com.niolasdev.randommouse.data.Cat

interface ICatRepository {

    suspend fun getCats(): CatResult<List<Cat>>
}