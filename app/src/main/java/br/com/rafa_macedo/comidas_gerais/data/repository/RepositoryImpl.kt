package br.com.rafa_macedo.comidas_gerais.data.repository

import br.com.rafa_macedo.comidas_gerais.data.datasource.RecipeDataSource
import br.com.rafa_macedo.comidas_gerais.data.response.Recipe
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RecipeDataSource) :
    Repository {
    override fun getRecipeInfo(isIncludeNutrition: Boolean): Recipe? {
        return remoteDataSource.getRecipe()
    }

}