package br.com.rafa_macedo.comidas_gerais.data.service

import br.com.rafa_macedo.comidas_gerais.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.data.response.RecipeSummary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipesService {

    @GET(value = "recipes/recipes/{id}/information")
    fun getInformation(
        @Path(value = "id") id: Long,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Response<RecipeInformation.Response>

    @GET(value = "recipes/recipes/{id}/summary")
    fun getSummary(
        @Path(value = "id") id: Long,
    ): Response<RecipeSummary.Response>

    @GET(value = "recipes/autocomplete")
    fun autoCompleteSearch(
        @Query("query") query: String,
        @Query("number") number: Int
    ): Response<RecipeAutoComplete.Response>
}