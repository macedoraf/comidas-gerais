package br.com.rafa_macedo.comidas_gerais.main.data.repository

import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSearchResponse

interface Repository {
    suspend fun getRecipeInfo(
        recipeId: Long,
        isIncludeNutrition: Boolean
    ): RecipeInformation.Response

    suspend fun requestRecipeAutoComplete(query: String): List<RecipeAutoComplete.Item>
    suspend fun searchRecipeByQuery(query: String): RecipeSearchResponse
}