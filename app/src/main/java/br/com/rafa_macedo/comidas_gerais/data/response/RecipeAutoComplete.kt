package br.com.rafa_macedo.comidas_gerais.data.response

sealed class RecipeAutoComplete {
    data class Request(val query: String, val number: Int)
    data class Response(val id: Long, val title: String, val imageType: String)

}
