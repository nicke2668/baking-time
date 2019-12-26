package com.example.bakingtime.view;

import java.util.List;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.ItemRecipeBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.view.RecipeAdapter.RecipeListViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeListViewHolder> {

	class RecipeListViewHolder extends RecyclerView.ViewHolder {

		private final ItemRecipeBinding binding;

		RecipeListViewHolder(ItemRecipeBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		void bind(Recipe recipe) {
			binding.setRecipe(recipe);
			considerLoadingDefaultDrawable(recipe);
		}

		private void considerLoadingDefaultDrawable(Recipe recipe) {
			if (recipe.getImage().isEmpty()) {
				Picasso.get().load(R.drawable.placeholder)
						.placeholder(R.drawable.placeholder)
						.into(binding.backdrop);
				return;
			}
			Picasso.get().load(recipe.getImage())
					.placeholder(R.drawable.placeholder)
					.into(binding.backdrop);
		}
	}

	private final AdapterItemClickCallback callback;
	private List<Recipe> recipes;

	RecipeAdapter(AdapterItemClickCallback<Recipe> callback) {
		this.callback = callback;
	}

	@Override
	public int getItemCount() {
		return recipes == null ? 0 : recipes.size();
	}

	@Override
	public void onBindViewHolder(final RecipeListViewHolder holder, int position) {
		holder.bind(recipes.get(position));
	}

	@NonNull
	@Override
	public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
		ItemRecipeBinding binding =
				DataBindingUtil.inflate(layoutInflater, R.layout.item_recipe, parent, false);
		binding.setCallback(callback);
		return new RecipeListViewHolder(binding);
	}

	public void setList(List<Recipe> recipes) {
		this.recipes = recipes;
		notifyDataSetChanged();
	}
}
