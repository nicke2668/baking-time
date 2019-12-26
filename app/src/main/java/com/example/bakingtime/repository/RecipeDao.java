package com.example.bakingtime.repository;

import java.util.List;

import com.example.bakingtime.model.Recipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface RecipeDao {

	@Insert
	void insertRecipes(List<Recipe> recipes);

	@Transaction
	@Query("SELECT * FROM Recipe")
	LiveData<List<Recipe>> loadAllRecipes();

	@Transaction
	@Query("SELECT * FROM Recipe WHERE id = :id")
	Recipe loadRecipeById(int id);
}

