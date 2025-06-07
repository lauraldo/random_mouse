package com.niolasdev.randommouse.domain

import android.util.Log
import com.niolasdev.network.CatApiService
import com.niolasdev.network.CatDto
import com.niolasdev.randommouse.data.BreedDboMapper
import com.niolasdev.randommouse.data.BreedMapper
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.data.CatDboMapper
import com.niolasdev.randommouse.data.CatMapper
import com.niolasdev.storage.CatsDatabase
import com.niolasdev.storage.model.CatDbo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CatRepository @Inject constructor(
    private val apiService: CatApiService,
    private val database: CatsDatabase,
) : ICatRepository {

    private val catDtoMapper: CatMapper by lazy {
        CatMapper(breedMapper = BreedMapper())
    }

    private val catDboMapper: CatDboMapper by lazy {
        CatDboMapper(breedDboMapper = BreedDboMapper())
    }

    override suspend fun getCats(): Flow<CatResult<List<Cat>>> {
        val catsFromDB: Flow<CatResult<List<Cat>>> = getCatsFromDB()
        val catsFromNetwork: Flow<CatResult<List<Cat>>> = getCatsFromNetwork()

        return catsFromDB
            // TODO  .combine(catsFromNetwork, requestResponseMergeStrategy::merge)
            .flatMapLatest { result ->
                if (result is CatResult.Success<*>) {
                    database.catsDao.observeAll()
                        .map { catsDBO -> catsDBO.map { catDboMapper.from(it) } }
                        .map { CatResult.Success(it) }
                } else {
                    flowOf(result)
                }
            }
    }

    private fun getCatsFromDB(): Flow<CatResult<List<Cat>>> {
        val dbRequest =
            database.catsDao::getAll.asFlow()
                .map<List<CatDbo>, CatResult<List<CatDbo>>> { CatResult.Success(it) }
                .catch {
                    emit(CatResult.Error(e = it))
                }

        val start = flowOf<CatResult<List<CatDbo>>>(CatResult.InProgress())

        return merge(start, dbRequest).map { result ->
            result.map { catDBO ->
                catDBO.map { catDboMapper.from(it) }
            }
        }
    }

    private fun getCatsFromNetwork(): Flow<CatResult<List<Cat>>> {
        val apiRequest = flow { emit(apiService.searchCats()) }
            .onEach { result ->
                //if (result.isSuccess) saveResponseToDatabase(result.getOrThrow().articles)
            }
            .onEach { result ->
                when {
                    result.isSuccess -> {
                        val data = result.getOrNull()?.map {
                            catDtoMapper.from(it)
                        } ?: emptyList<Cat>()

                        CatResult.Success(data)
                    }

                    result.isFailure -> {
                        Log.e("CatRepository", result.exceptionOrNull()?.message ?: "")
                        CatResult.Error(result.exceptionOrNull())
                    }

                    else -> CatResult.Error(null)
                }
            }
            .map { it.toCatResult() }

        val start = flowOf<CatResult<List<CatDto>>>(CatResult.InProgress())
        return merge(apiRequest, start)
            .map { result ->
                result.map { response -> response.map { catDtoMapper.from(it) } }
            }
    }
}