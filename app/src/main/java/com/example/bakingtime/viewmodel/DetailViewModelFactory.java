package com.example.bakingtime.viewmodel;

import com.example.bakingtime.model.Recipe;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider.Factory;

public class DetailViewModelFactory implements Factory {

	private final Recipe recipe;

	public DetailViewModelFactory(Recipe recipe) {
		this.recipe = recipe;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new DetailViewModel(recipe);
	}
}
