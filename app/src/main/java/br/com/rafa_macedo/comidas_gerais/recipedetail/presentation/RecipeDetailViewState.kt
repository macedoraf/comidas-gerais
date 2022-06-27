package br.com.rafa_macedo.comidas_gerais.recipedetail.presentation

import androidx.compose.runtime.snapshots.SnapshotStateList
import br.com.rafa_macedo.comidas_gerais.model.Ingredient

data class RecipeDetailViewState(
    val recipeDescription: String,
    val recipeIngredients: SnapshotStateList<Ingredient>,
    val recipeSteps: SnapshotStateList<String>
) {
    companion object {
        val Initial = RecipeDetailViewState(
            recipeDescription = "",
            recipeIngredients = SnapshotStateList(),
            recipeSteps = SnapshotStateList()
        )
    }
}