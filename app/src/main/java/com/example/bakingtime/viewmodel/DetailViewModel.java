package com.example.bakingtime.viewmodel;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.bakingtime.PreferenceManagerClient;
import com.example.bakingtime.R;
import com.example.bakingtime.model.Ingredient;
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

	public long exoPlayerPreviousPosition;
	public boolean exoPlayerState;
	public int position;
	private Recipe recipe;
	private final RecipeContentRepository repository = RecipeContentRepository.INSTANCE;
	private List<Step> steps;

	//required to persist viewmodel
	public DetailViewModel() {

	}

	DetailViewModel(Recipe recipe) {
		this.recipe = recipe;
		this.steps = recipe.getSteps();
	}

	public Step getCurrentStep() {
		return steps.get(position);
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

	public List<Ingredient> getIngredients() {
		return recipe.getIngredients();
	}

	public String getRecipeName() {
		return recipe.getName();
	}

	public void observeExoplayerStateChanges(StepsOverviewFragment stepsOverviewFragment, LifecycleOwner viewLifecycleOwner) {
		repository.getExoPlayerstateEmitter().addObserver(stepsOverviewFragment, viewLifecycleOwner);
	}

	public void observeNavigationStateChanges(StepFragment stepsOverviewFragment, LifecycleOwner viewLifecycleOwner) {
		repository.getNavigationStateEmitter().addObserver(stepsOverviewFragment, viewLifecycleOwner);
	}

	public List<Step> getSteps() {
		return steps;
	}

	public int getTotalStepCount() {
		return steps.size();
	}

	public void onNextStepClicked() {
		position++;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
		this.steps = recipe.getSteps();
	}

	public void onPreviousClicked() {
		position--;
	}
}
