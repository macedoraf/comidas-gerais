package br.com.rafa_macedo.comidas_gerais.data.datasource

import br.com.rafa_macedo.comidas_gerais.data.response.Recipe

interface RecipeDataSource {
    fun getRecipe(): Recipe?
}