package br.com.rafa_macedo.comidas_gerais.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafa_macedo.comidas_gerais.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    val predictions: List<Any> = mutableListOf()
    var query: String = ""

    val onSearchTextChange = { text: String ->
        val result = viewModelScope.launch {
            repository.requestRecipeAutoComplete(text)
        }
    }
}