package br.com.rafa_macedo.comidas_gerais.main.data.repository

import br.com.rafa_macedo.comidas_gerais.main.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.main.data.request.RecipeComplexSearchRequest
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSearchResponse
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RecipeDataSource) :
    Repository {

    override suspend fun getRecipeInfo(
        recipeId: Long,
        isIncludeNutrition: Boolean
    ): RecipeInformation.Response {
        return remoteDataSource.getInformation(
            RecipeInformation.Request(
                recipeId,
                isIncludeNutrition
            )
        )
    }

    override suspend fun requestRecipeAutoComplete(query: String): List<RecipeAutoComplete.Item> {
        val response = remoteDataSource.autoCompleteSearch(RecipeAutoComplete.Request(query, 2))
        return RecipeAutoComplete.Mapper.toItems(response)
    }

    override suspend fun searchRecipeByQuery(query: String): RecipeSearchResponse {
        return remoteDataSource.search(RecipeComplexSearchRequest(query))
    }
}