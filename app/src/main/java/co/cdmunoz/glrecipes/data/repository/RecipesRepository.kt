package co.cdmunoz.glrecipes.data.repository

import co.cdmunoz.glrecipes.data.api.ApiService
import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.data.model.RecipeDetails
import co.cdmunoz.glrecipes.utils.Result
import retrofit2.HttpException
import timber.log.Timber

class RecipesRepository(private val apiService: ApiService) : BaseRepository() {

    companion object {
        private val TAG = RecipesRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    suspend fun getRecipesFromApi(): Result<List<Recipe>> {
        var result: Result<List<Recipe>> = handleSuccess(arrayListOf())
        try {
            val response = apiService.getRecipes()
            response.body()?.let { recipesResponse ->
                result = handleSuccess(recipesResponse)
            }
            response.errorBody()?.let { responseErrorBody ->
                if (responseErrorBody is HttpException) {
                    responseErrorBody.response()?.code()?.let { errorCode ->
                        result = handleException(errorCode)
                    }
                } else result = handleException(GENERAL_ERROR_CODE)
            }
        } catch (error: HttpException) {
            return handleException(error.code())
        }
        return result
    }

    suspend fun getRecipeDetailsFromApi(recipeId: Int): Result<RecipeDetails> {
        var result: Result<RecipeDetails> = handleSuccess(RecipeDetails())
        try {
            val response = apiService.getRecipeDetails(recipeId)
            response.body()?.let { recipesResponse ->
                result = handleSuccess(recipesResponse)
            }
            response.errorBody()?.let { responseErrorBody ->
                if (responseErrorBody is HttpException) {
                    responseErrorBody.response()?.code()?.let { errorCode ->
                        result = handleException(errorCode)
                    }
                } else result = handleException(GENERAL_ERROR_CODE)
            }
        } catch (error: HttpException) {
            return handleException(error.code())
        }
        return result
    }

}