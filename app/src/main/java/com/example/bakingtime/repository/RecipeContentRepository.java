package com.example.bakingtime.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import com.example.bakingtime.AsyncExecutor;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.repository.network.GetRecipeDataConsumer;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeContentRepository implements GetRecipeDataConsumer, AsyncExecutor, RecipeDatabaseClient {

	public static final RecipeContentRepository INSTANCE = new RecipeContentRepository();
	private final MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

	private RecipeContentRepository() {
	}

	private LiveData<List<Recipe>> considerFetchingRecipes(List<Recipe> recipes) {
		return recipes == null || recipes.isEmpty() ? fetchRecipes() : toLiveData(recipes);
	}

	private Collection<Callable<Void>> createStoreRecipeAsyncCallable(List<Recipe> results) {
		return Collections.singleton(() -> {
			getRecipeDao().insertRecipes(results);
			return NOTHING;
		});
	}

	private LiveData<List<Recipe>> fetchRecipes() {
		getService().getRecipes().enqueue(new Callback<List<Recipe>>() {
			@Override
			public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable throwable) {
				recipesLiveData.setValue(null);
			}

			@Override
			public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
				if (response.isSuccessful() && response.body() != null) {
					List<Recipe> results = response.body();
					recipesLiveData.setValue(results);
					executeSingleThreadAsync(createStoreRecipeAsyncCallable(results));
				}
			}
		});

		return recipesLiveData;
	}

	public LiveData<List<Recipe>> loadRecipes() {
		return Transformations.switchMap(getRecipeDao().loadAllRecipes(), this::considerFetchingRecipes);

	}

	private LiveData<List<Recipe>> toLiveData(List<Recipe> recipes) {
		recipesLiveData.postValue(recipes);
		return recipesLiveData;
	}
}
