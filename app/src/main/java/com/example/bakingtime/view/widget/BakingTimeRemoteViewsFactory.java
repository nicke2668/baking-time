package com.example.bakingtime.view.widget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingtime.PreferenceManagerClient;
import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.repository.RecipeDatabaseClient;

public class BakingTimeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory, RecipeDatabaseClient, PreferenceManagerClient {

	private WeakReference<Context> contextWeakReference;
	private final List<String> ingredients = new ArrayList<>();

	BakingTimeRemoteViewsFactory(Context context) {
		this.contextWeakReference = new WeakReference<>(context);
	}

	@Override
	public int getCount() {
		return ingredients.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {
		if (position == AdapterView.INVALID_POSITION || ingredients.isEmpty()) {
			return null;
		}
		RemoteViews remoteViews = new RemoteViews(contextWeakReference.get().getPackageName(), R.layout.widget_list_item);
		remoteViews.setTextViewText(R.id.widget_item_textview, ingredients.get(position));
		remoteViews.setOnClickFillInIntent(R.id.widget_item_textview, new Intent());
		return remoteViews;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
		//do nothing
	}

	@Override
	public void onDataSetChanged() {
		int recipeId = getWidgetRecipeId(contextWeakReference.get());
		if (recipeId != -1) {
			ingredients.clear();
			Recipe recipe = getRecipeDao().loadRecipeById(recipeId);
			if (recipe != null) {
				for (Ingredient ingredient : recipe.getIngredients()) {
					ingredients.add(String.format(Locale.getDefault(), "%.1f %s %s",
							ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		//do nothing
	}
}
