package com.example.bakingtime.repository.network;

import java.util.ArrayList;
import java.util.List;

import com.example.bakingtime.model.Recipe;
import com.google.gson.annotations.SerializedName;

public class RecipesResponse {

	@SerializedName("results")
	private List<Recipe> recipeResults = new ArrayList<>();

	public List<Recipe> getRecipeResults() {
		return recipeResults;
	}

	public void setRecipeResults(List<Recipe> recipeResults) {
		this.recipeResults = recipeResults;
	}
}
