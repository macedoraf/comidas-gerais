package br.com.rafa_macedo.comidas_gerais.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList

data class MainViewState(val querySearchState: QuerySearchState) {

    companion object {
        val Initial = MainViewState(QuerySearchState.Initial)
    }

    data class QuerySearchState(
        val query: String,
        val showClearIcon: Boolean,
        val predictions: SnapshotStateList<String>
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