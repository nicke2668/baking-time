package com.example.bakingtime.repository;

import com.example.bakingtime.model.Recipe;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

	public abstract RecipeDao getRecipeDao();
}
