package com.example.bakingtime.model;

import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.room.TypeConverter;

public class DataTypeConverter {

	private static final Gson GSON = new Gson();

	@TypeConverter
	public static String ingredientListToString(List<Ingredient> ingredients) {
		return GSON.toJson(ingredients);
	}

	@TypeConverter
	public static String stepListToString(List<Step> steps) {
		return GSON.toJson(steps);
	}

	@TypeConverter
	public static List<Ingredient> stringToIngredientList(String data) {
		if (data == null) {
			return Collections.emptyList();
		}

		return GSON.fromJson(data, new TypeToken<List<Ingredient>>() {
		}.getType());
	}

	@TypeConverter
	public static List<Step> stringToStepList(String data) {
		if (data == null) {
			return Collections.emptyList();
		}

		return GSON.fromJson(data, new TypeToken<List<Step>>() {
		}.getType());
	}
}
