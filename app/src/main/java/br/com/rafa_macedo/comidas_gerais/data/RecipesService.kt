package br.com.rafa_macedo.comidas_gerais.data

import br.com.rafa_macedo.comidas_gerais.data.response.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipesService {

    @GET(value = "recipes/recipes/{id}/information")
    fun getRecipeInformation(
        @Path(value = "id") id: Long,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Call<Recipe>
}