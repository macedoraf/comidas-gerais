package br.com.rafa_macedo.comidas_gerais.data.datasource

import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.service.RecipesService
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeSummary

class RecipeDataRemoteSource(private val recipesService: RecipesService) : RecipeDataSource {

    override suspend fun getInformation(request: RecipeInformation.Request): RecipeInformation.Response {
        val response = recipesService.getInformation(request.id, false)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        throw RuntimeException()
    }

    override suspend fun getSummary(request: RecipeSummary.Request): RecipeSummary.Response {
        val response = recipesService.getSummary(request.id)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        throw RuntimeException()
    }

    override suspend fun autoCompleteSearch(request: RecipeAutoComplete.Request): List<RecipeAutoComplete.Response> {
        val response = recipesService.autoCompleteSearch(request.query, request.number)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        throw RuntimeException()
    }
}