package br.com.rafa_macedo.comidas_gerais.main.data.response

data class RecipeSimpleItem(
    val id: Long,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val imageType: String,
    val protein: String
) {
}