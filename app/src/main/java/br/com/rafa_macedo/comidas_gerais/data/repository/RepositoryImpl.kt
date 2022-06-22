package br.com.rafa_macedo.comidas_gerais.data.repository

import br.com.rafa_macedo.comidas_gerais.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RecipeDataSource) :
    Repository {
    override fun getRecipeInfo(isIncludeNutrition: Boolean): RecipeInformation.Response {
        return remoteDataSource.getInformation(RecipeInformation.Request(0, isIncludeNutrition))
    }

}