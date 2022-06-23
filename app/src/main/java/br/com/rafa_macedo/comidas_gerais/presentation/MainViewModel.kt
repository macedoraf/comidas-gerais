package br.com.rafa_macedo.comidas_gerais.presentation

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafa_macedo.comidas_gerais.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    private val _viewState = MutableStateFlow(MainViewState.Initial)
    val viewState = _viewState.asStateFlow()

    private val pendingActions = MutableSharedFlow<MainAction>()
    private val searchBarPendingUpdate = MutableSharedFlow<String>()

    init {
        viewModelScope.launch {
            searchBarPendingUpdate
                .debounce(1500)
                .collectLatest { query ->
                    val predictions = repository.requestRecipeAutoComplete(query)
                        .map { recipe -> recipe.title }
                    val newState =
                        _viewState
                            .value
                            .querySearchState.copy(predictions = predictions.toMutableStateList())
                    _viewState.emit(_viewState.value.copy(querySearchState = newState))
                }

            pendingActions.collect {}
        }
    }

    val onItemClick: (Any) -> Unit = {}
    val onClearClick: () -> Unit = {}
    val onDoneActionClick: () -> Unit = {}

    val onQueryChanged: (String) -> Unit = { text: String ->
        viewModelScope.launch(Dispatchers.IO) {
            val state = _viewState.value.querySearchState.copy(
                query = text,
                showClearIcon = text.isNotBlank(),
            )

            if (text.isEmpty()) {
                state.predictions.clear()
            }
            _viewState.emit(_viewState.value.copy(querySearchState = state))
            searchBarPendingUpdate.emit(text)
        }
    }
}