package br.com.rafa_macedo.comidas_gerais.model

data class Ingredient(
    val aisle: String,
    val amount: Int,
    val consistency: String,
    val id: Long,
    val image: String,
    val measures: Measures,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String
) {
}