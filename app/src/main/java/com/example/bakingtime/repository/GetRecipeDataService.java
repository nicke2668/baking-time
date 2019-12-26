package com.example.bakingtime.repository;

import java.util.List;

import com.example.bakingtime.model.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRecipeDataService {

	@GET("baking.json")
	Call<List<Recipe>> getRecipes();
}
