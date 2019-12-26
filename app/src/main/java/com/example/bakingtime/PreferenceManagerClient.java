package com.example.bakingtime;

import android.content.Context;

import androidx.preference.PreferenceManager;

public interface PreferenceManagerClient {

	default int getWidgetRecipeId(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getInt(context.getString(R.string.pref_widget_key), -1);
	}

	default String getWidgetTitle(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(context.getString(R.string.pref_title_key),
						context.getString(R.string.widget_text));
	}

	default void setWidgetRecipeId(Context context, int recipeId) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putInt(context.getString(R.string.pref_widget_key), recipeId)
				.apply();
	}

	default void setWidgetTitle(Context context, String title) {
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putString(context.getString(R.string.pref_title_key), title)
				.apply();
	}
}
