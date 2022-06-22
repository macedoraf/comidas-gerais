package br.com.rafa_macedo.comidas_gerais.data.datasource

import br.com.rafa_macedo.comidas_gerais.data.RecipesService
import br.com.rafa_macedo.comidas_gerais.data.response.Recipe

class RecipeDataRemoteSource(private val recipesService: RecipesService) : RecipeDataSource {
    override fun getRecipe(): Recipe? {
        val response = recipesService.getRecipeInformation(1, false).execute()
        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }
}