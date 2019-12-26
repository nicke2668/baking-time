package com.example.bakingtime.repository;

import com.example.bakingtime.persistence.RecipeDatabaseValueHolder;

public interface RecipeDatabaseClient {

	default RecipeDao getRecipeDao() {
		return RecipeDatabaseValueHolder.INSTANCE.getValue().getRecipeDao();
	}
}
