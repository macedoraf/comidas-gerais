package br.com.rafa_macedo.comidas_gerais.recipedetail.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rafa_macedo.comidas_gerais.main.data.repository.Repository
import br.com.rafa_macedo.comidas_gerais.model.Ingredient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), LifecycleObserver {

    private val _viewState = MutableStateFlow(RecipeDetailViewState.Initial)
    val viewState = _viewState.asStateFlow()

    private val pendingActions = MutableSharedFlow<RecipeDetailViewAction>()

    init {
        subscribeEventActions()
    }

    private fun subscribeEventActions() {
        viewModelScope.launch(dispatcherIo) {
            pendingActions.collect { action ->
                with(action) {
                    when (this) {
                        RecipeDetailViewAction.TimerClick -> {}
                    }
                }
            }
        }
    }


    fun requestRecipeInfo(recipeId: Long) {
        viewModelScope.launch(dispatcherIo) {
            val result = repository.getRecipeInfo(recipeId, false)
            val newState = _viewState.value.copy(
                recipeDescription = result.summary,
                recipeIngredients = SnapshotStateList<Ingredient>()
            )
            _viewState.emit(newState)
        }
    }

}