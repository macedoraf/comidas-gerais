package br.com.rafa_macedo.comidas_gerais.main.data.response

import br.com.rafa_macedo.comidas_gerais.model.Ingredient

sealed class RecipeInformation {
    data class Response(
        val id: Long,
        val title: String,
        val image: String,
        val imageType: String,
        val servings: Int,
        val summary: String,
        val extendedIngredients: List<Ingredient>
    ) :
        RecipeInformation()

    data class Request(val id: Long, val includeNutrition: Boolean) : RecipeInformation()
}