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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catRepository: ICatRepository,
    private val appDispatchers: AppDispatchers,
) : ViewModel() {

    val uiState: StateFlow<CatListState>
        get() = catRepository.getCats().map { res ->
                when (res) {
                    is CatResult.Error<*> -> {
                        Log.d("MURLO", res.e?.message ?: "something went wrong")
                        Error(res.e?.message ?: "")
                    }

                    is CatResult.Success<List<Cat>> -> {
                        Log.d("MURLO", res.data.toString())
                        Data(res.data)
                    }

                    is CatResult.InProgress<*> -> {
                        Log.d("MURLO", "loading in progress")
                        Loading
                    }
                }
            }.stateIn(viewModelScope, SharingStarted.Lazily, Loading)

}

sealed interface CatListState {
    object Loading : CatListState
    class Data(val cats: List<Cat>) : CatListState
    class Error(val message: String) : CatListState
}