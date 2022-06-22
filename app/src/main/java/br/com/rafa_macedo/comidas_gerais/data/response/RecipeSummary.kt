package br.com.rafa_macedo.comidas_gerais.data.response

sealed class RecipeSummary {
    data class Response(val id: Long, val summary: String, val title: String) : RecipeSummary()
    data class Request(val id: Long) : RecipeSummary()
}