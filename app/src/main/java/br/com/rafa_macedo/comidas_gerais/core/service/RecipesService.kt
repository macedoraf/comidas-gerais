package br.com.rafa_macedo.comidas_gerais.core.service

import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeAutoComplete
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeInformation
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSearchResponse
import br.com.rafa_macedo.comidas_gerais.main.data.response.RecipeSummary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface RecipesService {

    @GET(value = "recipes/{id}/information")
    suspend fun getInformation(
        @Path(value = "id") id: Long,
        @Query("includeNutrition") includeNutrition: Boolean
    ): Response<RecipeInformation.Response>

    @GET(value = "recipes/recipes/{id}/summary")
    suspend fun getSummary(
        @Path(value = "id") id: Long,
    ): Response<RecipeSummary.Response>

    @GET(value = "recipes/complexSearch")
    suspend fun search(
        @QueryMap mapString: Map<String, String>,
        @QueryMap mapNumber: Map<String, Int>
    ): Response<RecipeSearchResponse>

    @GET(value = "recipes/complexSearch")
    suspend fun search(@QueryMap mapString: Map<String, String>
    ): Response<RecipeSearchResponse>


    @GET(value = "recipes/autocomplete")
    suspend fun autoCompleteSearch(
        @Query("query") query: String,
        @Query("number") number: Int
    ): Response<List<RecipeAutoComplete.Response>>
}