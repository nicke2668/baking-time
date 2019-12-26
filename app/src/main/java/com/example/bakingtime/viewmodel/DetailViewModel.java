package com.example.bakingtime.viewmodel;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.bakingtime.PreferenceManagerClient;
import com.example.bakingtime.R;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.view.widget.BakingTimeWidget;

import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel implements PreferenceManagerClient {

	public Step currentStep;
	public boolean playState;
	public long previousPosition;
	public final Recipe recipe;
	public int stepNumber;
	private int stepPosition;
	private final List<Step> steps;

	DetailViewModel(Recipe recipe) {
		this.recipe = recipe;
		this.steps = recipe.getSteps();
		this.currentStep = steps.get(0);
	}

	public void addWidget(Context context) {
		setWidgetTitle(context, recipe.getName());
		setWidgetRecipeId(context, recipe.getId());
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
				new ComponentName(context, BakingTimeWidget.class));
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview_ingredients);
		BakingTimeWidget.INSTANCE.updateAppWidget(context, appWidgetManager, appWidgetIds);
	}

	public Step getStep() {
		return currentStep;
	}

	public int getTotalStepCount() {
		return recipe.getSteps().size();
	}

	public void onNextStepClicked() {
		currentStep = steps.get(++stepPosition);
	}

	public void onPreviousClicked() {
		currentStep = steps.get(--stepPosition);
	}

}
