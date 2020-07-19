package co.cdmunoz.glrecipes.ui.recipes.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import co.cdmunoz.glrecipes.data.api.ApiService
import co.cdmunoz.glrecipes.data.api.RetrofitService
import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.data.model.RecipeDetails
import co.cdmunoz.glrecipes.databinding.ActivityRecipeDetailsBinding
import co.cdmunoz.glrecipes.ui.recipes.RecipesViewModel
import co.cdmunoz.glrecipes.ui.recipes.ViewModelFactory
import co.cdmunoz.glrecipes.utils.Result
import co.cdmunoz.glrecipes.utils.setImageFromUrl

class RecipeDetailsActivity : AppCompatActivity() {

    companion object {
        const val RECIPE_ARG = "RECIPE_ARG"
    }

    private val binding: ActivityRecipeDetailsBinding by lazy {
        ActivityRecipeDetailsBinding.inflate(
            layoutInflater
        )
    }
    private lateinit var recipeArg: Recipe
    private val viewModel: RecipesViewModel by viewModels {
        ViewModelFactory(RetrofitService.createService(ApiService::class.java))
    }
    private val toolbar: Toolbar by lazy { toolbar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadArguments()
        initUI()
        initObservers()
        recipeArg?.let {
            viewModel.fetchRecipeDetails(it.recipeId)
        }
    }

    private fun loadArguments() {
        intent.extras?.getParcelable<Recipe>(RECIPE_ARG)?.let {
            recipeArg = it
        }
    }

    private fun initUI() {

    }

    private fun initObservers() {
        viewModel.getRecipeDetails().observe(this@RecipeDetailsActivity, Observer { result ->
            when (result) {
                is Result.InProgress -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@RecipeDetailsActivity,
                        result.exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    renderDetails(result.data)
                }
            }
        })
    }

    private fun renderDetails(data: RecipeDetails) {
        //toolbar.title = data.recipeTitle
        supportActionBar?.title = data.recipeTitle
        binding.recipeImage.setImageFromUrl(data.imageUrl)
        binding.recipeInstructions.text = data.instructions
    }
}
