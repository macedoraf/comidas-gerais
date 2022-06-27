package br.com.rafa_macedo.comidas_gerais.main.data.datasource

import br.com.rafa_macedo.comidas_gerais.main.data.request.RecipeComplexSearchRequest
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.core.service.RecipesService
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSearchResponse
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSummary

class RecipeDataRemoteSource(private val recipesService: RecipesService) : RecipeDataSource {

    override suspend fun getInformation(request: RecipeInformation.Request): RecipeInformation.Response {
        val response = recipesService.getInformation(request.id, false)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        throw RuntimeException(response.message())
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

    override suspend fun search(request: RecipeComplexSearchRequest): RecipeSearchResponse {
        val mapString = mutableMapOf(
            "query" to request.query
        )
        val mapNumber = mutableMapOf(
            "number" to 10,
            "offset" to 0
        )

        val response = recipesService.search(mapString = mapString, mapNumber = mapNumber)

        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        }

        throw RuntimeException()
    }
}