package com.niolasdev.randommouse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.domain.CatResult
import com.niolasdev.randommouse.domain.ICatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catRepository: ICatRepository,
    private val appDispatchers: AppDispatchers,
): ViewModel() {

    private val _state = MutableStateFlow<CatListState>(CatListState.Loading)

    val uiState: StateFlow<CatListState>
        get() = _state.asStateFlow()

    fun getCats() {
        viewModelScope.launch(appDispatchers.io) {
            delay(1000)
            val res = catRepository.getCats()
            _state.value = when (res) {
                is CatResult.Error -> {
                    Log.d("MURLO", res.e?.message ?: "something went wrong")
                    CatListState.Error(res.e?.message ?: "")
                }
                is CatResult.Success -> {
                    Log.d("MURLO", res.data.toString())
                    CatListState.Data(res.data)
                }
            }
        }
    }
}

sealed interface CatListState {
    object Loading: CatListState
    class Data(val cats: List<Cat>): CatListState
    class Error(val message: String): CatListState
}