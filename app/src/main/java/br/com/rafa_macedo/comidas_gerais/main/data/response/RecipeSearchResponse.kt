package br.com.rafa_macedo.comidas_gerais.main.data.response

data class RecipeSearchResponse(
    val offset: Int,
    val number: Int,
    val totalResults: Int,
    val results: List<RecipeSimpleItem>
) {
}