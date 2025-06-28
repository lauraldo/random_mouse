package com.niolasdev.randommouse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niolasdev.randommouse.CatListState.Data
import com.niolasdev.randommouse.CatListState.Error
import com.niolasdev.randommouse.CatListState.Loading
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.domain.CatResult
import com.niolasdev.randommouse.domain.ICatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catRepository: ICatRepository,
) : ViewModel() {

    private val _refreshTrigger = MutableStateFlow(0)

    private val _selectedCat = MutableStateFlow<Cat?>(null)

    val selectedCat: StateFlow<Cat?> = _selectedCat.asStateFlow()

    val uiState: StateFlow<CatListState> = _refreshTrigger
        .flatMapLatest { 
            catRepository.getCats()
        }
        .map { res ->
            when (res) {
                is CatResult.Error<*> -> {
                    Log.d(LOG_TAG, "Error: ${res.e?.message ?: "Unknown error"}")
                    Error(res.e?.message ?: "Unknown error")
                }
                is CatResult.Success<List<Cat>> -> {
                    Log.d(LOG_TAG, "Success: ${res.data.size} cats loaded")
                    Data(res.data)
                }
                is CatResult.InProgress<*> -> {
                    Log.d(LOG_TAG, "Loading in progress")
                    Loading
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Loading
        )

    fun refresh() {
        Log.d(LOG_TAG, "Refreshing cats data")
        _refreshTrigger.value = _refreshTrigger.value + 1
    }

    fun selectCat(cat: Cat) {
        _selectedCat.value = cat
    }

    fun clearSelectedCat() {
        Log.d(LOG_TAG, "Clearing selected cat")
        _selectedCat.value = null
    }
}

private const val LOG_TAG = "CatsViewModel"

sealed interface CatListState {
    object Loading : CatListState
    class Data(val cats: List<Cat>) : CatListState
    class Error(val message: String) : CatListState
}