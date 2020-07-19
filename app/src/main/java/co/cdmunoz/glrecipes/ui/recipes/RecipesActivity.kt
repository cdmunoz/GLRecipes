package co.cdmunoz.glrecipes.ui.recipes

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.cdmunoz.glrecipes.R
import co.cdmunoz.glrecipes.data.api.ApiService
import co.cdmunoz.glrecipes.data.api.RetrofitService
import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.databinding.ActivityRecipesBinding
import co.cdmunoz.glrecipes.ui.recipes.details.RecipeDetailsActivity
import co.cdmunoz.glrecipes.utils.Result

class RecipesActivity : AppCompatActivity(), RecipesAdapter.OnRecipeClickListener {

    private val binding: ActivityRecipesBinding by lazy {
        ActivityRecipesBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: RecipesViewModel by viewModels {
        ViewModelFactory(RetrofitService.createService(ApiService::class.java))
    }
    private lateinit var recipesAdapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(binding.root)
        initUI()
        initRecyclerView()
        initSearchView()
        initObservers()
        viewModel.loadData()
    }

    private fun initUI() {

    }

    private fun initSearchView() {
        with(binding.searchView) {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = false
                override fun onQueryTextChange(newText: String?): Boolean {
                    query?.let {
                        recipesAdapter.filter.filter(it)
                    }
                    return false
                }
            })
        }
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@RecipesActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@RecipesActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun initObservers() {
        viewModel.getRecipes().observe(this@RecipesActivity, Observer { result ->
            when (result) {
                is Result.InProgress -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@RecipesActivity,
                        result.exception.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    renderList(result.data)
                }
            }
        })
    }

    private fun renderList(data: List<Recipe>) {
        binding.recyclerView.apply {
            recipesAdapter = RecipesAdapter(data, this@RecipesActivity)
            adapter = recipesAdapter
        }
    }

    override fun onRecipeClicked(recipe: Recipe) {
        val intent = Intent(this@RecipesActivity, RecipeDetailsActivity::class.java).apply {
            putExtras(bundleOf(RecipeDetailsActivity.RECIPE_ARG to recipe))
        }
        startActivity(intent)
    }
}
