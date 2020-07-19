package co.cdmunoz.glrecipes.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.cdmunoz.glrecipes.data.api.ApiService
import co.cdmunoz.glrecipes.data.repository.RecipesRepository

class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(RecipesRepository(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name $modelClass")
    }
}