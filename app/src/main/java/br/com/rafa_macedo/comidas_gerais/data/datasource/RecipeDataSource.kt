package br.com.rafa_macedo.comidas_gerais.data.datasource

import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeSummary

interface RecipeDataSource {
    fun getInformation(request: RecipeInformation.Request): RecipeInformation.Response
    fun getSummary(request: RecipeSummary.Request): RecipeSummary.Response
    fun autoCompleteSearch(request: RecipeAutoComplete.Request): RecipeAutoComplete.Response
}