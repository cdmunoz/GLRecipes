package co.cdmunoz.glrecipes.ui.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import co.cdmunoz.glrecipes.data.model.Recipe
import co.cdmunoz.glrecipes.databinding.RowReceiptBinding


class RecipesAdapter constructor(recipesList: List<Recipe>, private val onRecipeClickListener: OnRecipeClickListener) :
    RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>(), Filterable {

    private val searchList = recipesList
    private val fullList = recipesList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowReceiptBinding.inflate(inflater, parent, false)
        return RecipesViewHolder(binding)
    }

    override fun getItemCount() = fullList.size

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(fullList[position])
    }

    inner class RecipesViewHolder(rowBinding: RowReceiptBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.recipeTitle
            binding.root.setOnClickListener {
                onRecipeClickListener.onRecipeClicked(recipe)
            }
        }
    }

    override fun getFilter() = eventFilter

    private val eventFilter: Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filteredList: MutableList<Recipe> = ArrayList()
            if (charSequence.isEmpty()) {
                filteredList.addAll(searchList)
            } else {
                val filterPattern =
                    charSequence.toString().toLowerCase().trim { it <= ' ' }
                for (item in searchList) {
                    if (item.recipeTitle.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            fullList.clear()
            fullList.addAll(results.values as Collection<Recipe>)
            notifyDataSetChanged()
        }
    }

    interface OnRecipeClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }
}