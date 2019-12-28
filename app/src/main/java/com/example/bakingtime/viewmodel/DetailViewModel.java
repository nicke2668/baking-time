package com.example.bakingtime.viewmodel;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.bakingtime.PreferenceManagerClient;
import com.example.bakingtime.R;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.repository.RecipeContentRepository;
import com.example.bakingtime.repository.RecipeContentRepository.ExoPlayerState;
import com.example.bakingtime.view.StepFragment;
import com.example.bakingtime.view.StepsOverviewFragment;
import com.example.bakingtime.view.widget.BakingTimeWidget;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

// TODO: 12/27/2019 use ObservableField to bind to UI directly
public class DetailViewModel extends ViewModel implements PreferenceManagerClient {

	public Step currentStep;
	public long exoPlayerPreviousPosition;
	public boolean exoPlayerState;
	public int stepNumber;
	public Recipe recipe;
	private RecipeContentRepository repository = RecipeContentRepository.INSTANCE;
	public int stepPosition;
	public List<Step> steps;

	DetailViewModel(Recipe recipe) {
		this.recipe = recipe;
		this.steps = recipe.getSteps();
		this.currentStep = steps.get(0);
	}

	public DetailViewModel() {

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

	public void emitExoPlayerState(@ExoPlayerState int maximized) {
		repository.emitExoPlayerState(maximized);
	}

	public void emitNavigation(int index) {
		repository.emitNavigation(index);
	}

	public void observeExoplayerStateChanges(StepsOverviewFragment stepsOverviewFragment, LifecycleOwner viewLifecycleOwner) {
		repository.getExoPlayerstateEmitter().addObserver(stepsOverviewFragment, viewLifecycleOwner);
	}

	public void observeNavigationStateChanges(StepFragment stepsOverviewFragment, LifecycleOwner viewLifecycleOwner) {
		repository.getNavigationStateEmitter().addObserver(stepsOverviewFragment, viewLifecycleOwner);
	}

	public void onNextStepClicked() {
		currentStep = steps.get(++stepPosition);
	}

	public void onPreviousClicked() {
		currentStep = steps.get(--stepPosition);
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		this.steps = recipe.getSteps();
	}
}
