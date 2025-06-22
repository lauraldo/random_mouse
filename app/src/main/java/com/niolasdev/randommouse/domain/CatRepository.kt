package com.niolasdev.randommouse.domain

import android.util.Log
import com.niolasdev.network.CatApiService
import com.niolasdev.network.CatDto
import com.niolasdev.randommouse.data.BreedDataMapper
import com.niolasdev.randommouse.data.BreedDboMapper
import com.niolasdev.randommouse.data.BreedMapper
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.data.CatDataMapper
import com.niolasdev.randommouse.data.CatDboMapper
import com.niolasdev.randommouse.data.CatMapper
import com.niolasdev.storage.CatsDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatRepository @Inject constructor(
    private val apiService: CatApiService,
    private val database: CatsDatabase,
    private val networkMonitor: NetworkMonitor,
) : ICatRepository {

    private val catDtoMapper: CatMapper by lazy {
        CatMapper(breedMapper = BreedMapper())
    }

    private val catDboMapper: CatDboMapper by lazy {
        CatDboMapper(breedDboMapper = BreedDboMapper())
    }

    private val catDataMapper: CatDataMapper by lazy {
        CatDataMapper(breedDataMapper = BreedDataMapper())
    }

    private val _isRefreshing = MutableStateFlow(false)

    // TODO: save fetchTime to DataStore
    private var lastFetchTime: Long = 0
    private val cacheTimeout = 5 * 60 * 1000L // 5 минут

    override fun getCats(): Flow<CatResult<List<Cat>>> = flow {
        if (_isRefreshing.value) {
            Log.d(LOG_TAG, "Refresh already in progress, skipping")
            return@flow
        }

        try {
            _isRefreshing.value = true

            Log.d(LOG_TAG, "Starting cats fetch")

            // Проверяем сеть
            if (!networkMonitor.isOnline()) {
                Log.d(LOG_TAG, "No internet connection, returning cached data")
                val cachedCats = database.catsDao.getAll()
                if (cachedCats.isNotEmpty()) {
                    emit(CatResult.Success(cachedCats.map { catDboMapper.from(it) }))
                } else {
                    emit(CatResult.Error(e = NetworkError.NoConnection()))
                }
                return@flow
            }

            // Эмитим данные из кэша
            val cachedCats = database.catsDao.getAll()
            if (cachedCats.isNotEmpty()) {
                Log.d(LOG_TAG, "Emitting cached data: ${cachedCats.size} cats")
                emit(CatResult.Success(cachedCats.map { catDboMapper.from(it) }))
            }

            // Проверяем необходимость обновления
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastFetchTime < cacheTimeout && cachedCats.isNotEmpty()) {
                Log.d(LOG_TAG, "Cache is still valid, skipping API call")
                return@flow
            }

            // Делаем запрос к API
            Log.d(LOG_TAG, "Making API request")
            emit(CatResult.InProgress())

            val result = apiService.searchCats()
            if (result.isSuccess) {
                val cats = result.getOrThrow()
                Log.d(LOG_TAG, "API request successful: ${cats.size} cats")

                // Сохраняем в БД
                saveResponseToDatabase(cats)
                lastFetchTime = currentTime

                // Эмитим новые данные
                emit(CatResult.Success(cats.map { catDtoMapper.from(it) }))
            } else {
                Log.e(LOG_TAG, "API request failed: ${result.exceptionOrNull()?.message}")
                emit(CatResult.Error(e = result.exceptionOrNull() ?: NetworkError.UnknownError()))
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error in getCats: ${e.message}", e)
            emit(CatResult.Error(e = e))
        } finally {
            _isRefreshing.value = false
        }
    }

    private suspend fun saveResponseToDatabase(data: List<CatDto>) {
        try {
            val catsDbo = data.map { dto ->
                catDataMapper.from(dto)
            }
            database.catsDao.insertCats(catsDbo)
            Log.d(LOG_TAG, "Saved ${catsDbo.size} cats to database")
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error saving to database: ${e.message}", e)
        }
    }
}

private const val LOG_TAG = "CatRepository"