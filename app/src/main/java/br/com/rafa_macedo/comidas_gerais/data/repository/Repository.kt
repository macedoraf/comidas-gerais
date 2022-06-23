package br.com.rafa_macedo.comidas_gerais.data.repository

import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation

interface Repository {
    suspend fun getRecipeInfo(isIncludeNutrition: Boolean): RecipeInformation.Response
    suspend fun requestRecipeAutoComplete(query: String): RecipeAutoComplete.Response
}