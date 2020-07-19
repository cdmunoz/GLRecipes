package co.cdmunoz.glrecipes.data.api

import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.data.model.RecipeDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("recipes")
    suspend fun getRecipes(): Response<List<Recipe>>

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(@Path("id") recipeId: Int): Response<RecipeDetails>
}