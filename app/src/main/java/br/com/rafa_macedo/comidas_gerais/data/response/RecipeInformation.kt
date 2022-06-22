package br.com.rafa_macedo.comidas_gerais.data.response

sealed class RecipeInformation {
    data class Response(val id: Long, val title: String) : RecipeInformation()
    data class Request(val id: Long, val includeNutrition: Boolean) : RecipeInformation()
}