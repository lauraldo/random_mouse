package com.niolasdev.randommouse.domain

import android.util.Log
import com.niolasdev.network.CatApiService
import com.niolasdev.randommouse.data.BreedMapper
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.data.CatMapper
import com.niolasdev.storage.CatsDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val apiService: CatApiService,
    private val database: CatsDatabase,
) : ICatRepository {

    private val catMapper: CatMapper by lazy {
        CatMapper(breedMapper = BreedMapper())
    }

    override suspend fun getCats(): CatResult<List<Cat>> {
        val catsFlow = database.catsDao.getAll().asFlow()


    }

    private suspend fun getCatsFromNetworkWithCache(): Flow<List<Cat>> {
        val apiRequest = flow {
            emit(apiService.searchCats())
        }.onEach { result ->
            when {
                result.isSuccess -> {
                    val data = result.getOrNull()?.map {
                        catMapper.from(it)
                    } ?: emptyList<Cat>()

//                    database.catsDao

                    val success = CatResult.Success(data)
                    success
                }

                result.isFailure -> {
                    Log.e("CatRepository", result.exceptionOrNull()?.message ?: "")
                    CatResult.Error(result.exceptionOrNull())
                }

                else -> CatResult.Error(null)
            }
        }
    }
}