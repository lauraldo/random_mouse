package com.niolasdev.randommouse.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niolasdev.network.FLAG_API_BASE
import com.niolasdev.randommouse.data.Breed
import com.niolasdev.randommouse.data.Cat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CatDetailViewModel @Inject constructor() : ViewModel() {

    val uiState: StateFlow<CatDetailState> = flow {
        emit(
            CatDetailState.Data(
                Cat(
                    id = "1",
                    url = "https://cdn2.thecatapi.com/images/1.jpg",
                    breeds = listOf(
                        Breed(
                            id = "abyss",
                            name = "Abyssinian",
                            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                            description = "The Abyssinian is easy to care for and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
                            origin = "Egypt",
                            countryFlagUrl = "${FLAG_API_BASE}eg.svg",
                        )
                    )
                )
            )
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CatDetailState.Loading,
    )
}

sealed interface CatDetailState {
    class Data(val cat: Cat) : CatDetailState
    class Error(val message: String) : CatDetailState
    object Loading : CatDetailState

}