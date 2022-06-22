package br.com.rafa_macedo.comidas_gerais.data.repository

import br.com.rafa_macedo.comidas_gerais.data.response.Recipe

interface Repository {
    fun getRecipeInfo(isIncludeNutrition: Boolean): Recipe?
}