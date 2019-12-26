package com.example.bakingtime.viewmodel;

import java.util.List;

import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.repository.RecipeContentRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

	private LiveData<List<Recipe>> recipes;
	private final RecipeContentRepository repository = RecipeContentRepository.INSTANCE;

	public DashboardViewModel() {
		getRecipes();
	}

	public LiveData<List<Recipe>> getRecipes() {
		if (recipes == null) {
			recipes = repository.loadRecipes();
		}

		return recipes;
	}
}
