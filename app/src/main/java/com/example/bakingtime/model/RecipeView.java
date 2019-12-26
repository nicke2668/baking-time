package com.example.bakingtime.model;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class RecipeView {

	@Relation(parentColumn = "id",
			entityColumn = "recipeId")
	public List<Ingredient> ingredients;
	@Embedded
	public Recipe recipe;
	@Relation(parentColumn = "id",
			entityColumn = "recipeId")
	public List<Step> steps;
}
