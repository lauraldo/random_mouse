package com.niolasdev.randommouse.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.domain.ICatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val catRepository: ICatRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val catId = savedStateHandle.getStateFlow<String?>("catId", null)

    val uiState: StateFlow<CatDetailState> = catId
        .filterNotNull()
        .flatMapLatest {
            flowOf(
                catRepository.getCatById(catId = it)?.let { cat ->
                    CatDetailState.Data(cat)
                } ?: CatDetailState.Error("Cat not found")
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatDetailState.Loading
        )
}

sealed interface CatDetailState {
    class Data(val cat: Cat) : CatDetailState
    class Error(val message: String) : CatDetailState
    object Loading : CatDetailState

}