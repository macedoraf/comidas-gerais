package br.com.rafa_macedo.comidas_gerais.main.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafa_macedo.comidas_gerais.core.SingleShotEventBus
import br.com.rafa_macedo.comidas_gerais.main.data.repository.Repository
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSimpleItem
import br.com.rafa_macedo.comidas_gerais.recipedetail.ui.RecipeDetailActivity
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

    private val _viewEffects = SingleShotEventBus<MainEffect>()
    val viewEffects: Flow<MainEffect> = _viewEffects.events

    private val pendingActions = MutableSharedFlow<MainAction>()
    private val searchBarPendingUpdate = MutableSharedFlow<String>()

    init {
        subscribeEventActions()
    }

    fun onRecipeItemClick(item: RecipeSimpleItem) {
        viewModelScope.launch(Dispatchers.Main) {
            val params = mapOf(RecipeDetailActivity.RECIPE_ID to item.id)
            _viewEffects.postEvent(MainEffect.Navigate(RecipeDetailActivity::class.java, params))
        }
    }

    fun onDoneActionClick() {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelScope.launch(Dispatchers.Main) {
                pendingActions.emit(MainAction.SearchBarSubmit)
            }
        }
    }

    fun onAutoCompleteItemClick(item: RecipeAutoComplete.Item) {
        viewModelScope.launch(Dispatchers.Main) {
            val nState =
                _viewState.value.querySearchState.copy(
                    query = item.title,
                    showClearIcon = true,
                    predictions = SnapshotStateList()
                )
            _viewState.emit(_viewState.value.copy(querySearchState = nState))
        }

        viewModelScope.launch(Dispatchers.IO) {
            pendingActions.emit(MainAction.SearchBarSubmit)
        }
    }

    fun onClearClick() {
        viewModelScope.launch(Dispatchers.Main) {
            pendingActions.emit(MainAction.SearchBarClear)
        }
    }

    fun onQueryChanged(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _viewState.value.querySearchState.copy(
                query = text,
                showClearIcon = text.isNotBlank(),
            )

            if (text.isEmpty()) {
                state.predictions.clear()
            }

            _viewState.emit(_viewState.value.copy(querySearchState = state))
            //searchBarPendingUpdate.emit(text)
        }
    }

    private fun subscribeEventActions() {
        viewModelScope.launch {
            pendingActions.collect {
                when (it) {
                    is MainAction.SearchBarClear -> onQueryChanged("")
                    is MainAction.SearchBarSubmit -> {
                        val query = _viewState.value.querySearchState.query

                        if (query.isBlank()) return@collect

                        val newState =
                            repository.searchRecipeByQuery(query)
                                .let { response ->
                                    _viewState.value.recipeListViewState.copy(items = response.results.toMutableStateList())
                                }
                        _viewState.emit(_viewState.value.copy(recipeListViewState = newState))
                    }
                }
            }
        }
        viewModelScope.launch {
            searchBarPendingUpdate
                .debounce(800)
                .collectLatest { query -> handleQuerySearch(query) }
        }
    }

    private suspend fun handleQuerySearch(query: String) {
        val predicts = fetchRecipeAutoComplete(query)
        val state = _viewState.value.querySearchState.copy(predictions = predicts)
        _viewState.emit(_viewState.value.copy(querySearchState = state))
    }

    private suspend fun fetchRecipeAutoComplete(query: String):
            SnapshotStateList<RecipeAutoComplete.Item> =
        repository.requestRecipeAutoComplete(query).toMutableStateList()

}