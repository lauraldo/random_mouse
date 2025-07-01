package com.niolasdev.randommouse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niolasdev.randommouse.data.Cat
import com.niolasdev.randommouse.domain.ICatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val catRepository: ICatRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<CatDetailState>(CatDetailState.Loading)
    val uiState = _state.asStateFlow()

    fun onEvent(event: CatDetailEvent) {
        when (event) {
            is CatDetailEvent.CatRequested -> requestCat(event.catId)
        }
    }

    private fun requestCat(catId: String) {
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _state.value = catRepository.getCatById(catId = catId)?.let { cat ->
                CatDetailState.Data(cat)
            } ?: CatDetailState.Error("Cat not found")
        }
    }
}

sealed interface CatDetailEvent {
    class CatRequested(val catId: String): CatDetailEvent
}

sealed interface CatDetailState {
    class Data(val cat: Cat) : CatDetailState
    class Error(val message: String) : CatDetailState
    object Loading : CatDetailState

}