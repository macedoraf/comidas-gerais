package br.com.rafa_macedo.comidas_gerais.main.data.datasource

import br.com.rafa_macedo.comidas_gerais.main.data.request.RecipeComplexSearchRequest
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSearchResponse
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSummary

interface RecipeDataSource {
    suspend fun getInformation(request: RecipeInformation.Request): RecipeInformation.Response
    suspend fun getSummary(request: RecipeSummary.Request): RecipeSummary.Response
    suspend fun autoCompleteSearch(request: RecipeAutoComplete.Request): List<RecipeAutoComplete.Response>
    suspend fun search(request: RecipeComplexSearchRequest): RecipeSearchResponse
}