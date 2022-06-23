package br.com.rafa_macedo.comidas_gerais.data.datasource

import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeSummary

interface RecipeDataSource {
    suspend fun getInformation(request: RecipeInformation.Request): RecipeInformation.Response
    suspend fun getSummary(request: RecipeSummary.Request): RecipeSummary.Response
    suspend fun autoCompleteSearch(request: RecipeAutoComplete.Request): List<RecipeAutoComplete.Response>
}