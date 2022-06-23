package br.com.rafa_macedo.comidas_gerais.data.repository

import br.com.rafa_macedo.comidas_gerais.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RecipeDataSource) :
    Repository {

    override suspend fun getRecipeInfo(isIncludeNutrition: Boolean): RecipeInformation.Response {
        return remoteDataSource.getInformation(RecipeInformation.Request(0, isIncludeNutrition))
    }

    override suspend fun requestRecipeAutoComplete(query: String): RecipeAutoComplete.Response {
        return remoteDataSource.autoCompleteSearch(RecipeAutoComplete.Request(query, 2))
    }
}