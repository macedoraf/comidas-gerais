package br.com.rafa_macedo.comidas_gerais.main.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSimpleItem

data class MainViewState(
    val querySearchState: QuerySearchState,
    val recipeListViewState: RecipeListViewState
) {

    companion object {
        val Initial = MainViewState(QuerySearchState.Initial, RecipeListViewState.Initial)
    }

    data class RecipeListViewState(val items: SnapshotStateList<RecipeSimpleItem>) {
        companion object {
            val Initial = RecipeListViewState(SnapshotStateList())
        }
    }

    data class QuerySearchState(
        val query: String,
        val showClearIcon: Boolean,
        val predictions: SnapshotStateList<RecipeAutoComplete.Item>
    ) {
        companion object {
            val Initial = QuerySearchState(
                query = "",
                showClearIcon = false,
                predictions = SnapshotStateList()
            )
        }
    }
}