package co.cdmunoz.glrecipes.ui.recipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.data.model.RecipeDetails
import co.cdmunoz.glrecipes.data.repository.RecipesRepository
import co.cdmunoz.glrecipes.utils.Result
import kotlinx.coroutines.launch
import timber.log.Timber

class RecipesViewModel(private val recipesRepository: RecipesRepository): ViewModel() {

    private val recipes = MutableLiveData<Result<List<Recipe>>>()
    private val recipeDetails = MutableLiveData<Result<RecipeDetails>>()

    fun getRecipes() = recipes
    fun getRecipeDetails() = recipeDetails

    fun loadData() {
        recipes.postValue(Result.InProgress)
        viewModelScope.launch { 
            val result = recipesRepository.getRecipesFromApi()
            recipes.postValue(result)
        }
    }

    fun fetchRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            recipeDetails.postValue(recipesRepository.getRecipeDetailsFromApi(recipeId))
        }
    }
}