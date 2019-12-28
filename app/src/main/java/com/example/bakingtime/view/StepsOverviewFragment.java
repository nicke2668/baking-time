package com.example.bakingtime.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.StepsOverviewFragmentBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.repository.EmittedStateObserver;
import com.example.bakingtime.repository.RecipeContentRepository.ExoPlayerState;
import com.example.bakingtime.viewmodel.DetailViewModel;
import com.example.bakingtime.viewmodel.DetailViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StepsOverviewFragment extends RecipeDetailFragment implements EmittedStateObserver {

	private final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
	private DetailViewModel viewModel;
	private final StepsAdapter stepsAdapter = new StepsAdapter(this::onWatchClicked);
	private StepsOverviewFragmentBinding binding;

	private void considerInitializingMasterDetailFlow(boolean firstTime) {
		if (!firstTime || !isTablet()) return;
		initializeStepFragmentPane();
	}

	private Recipe getRecipeDetailsFromBundle() {
		return StepsOverviewFragmentArgs.fromBundle(requireArguments()).getRecipe();
	}

	private void initializeStepFragmentPane() {
		getChildFragmentManager()
				.beginTransaction()
				.add(R.id.stepFragmentPlaceholder, new StepFragment())
				.commit();
	}

	private void initializeViews(boolean firstTime) {
		binding.setCallback(this::onClickAddWidget);
		stepsAdapter.setList(viewModel.getSteps());
		binding.stepsRecyclerView.setAdapter(stepsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		ingredientsAdapter.setList(viewModel.getIngredients());
		binding.ingredientsRecyclerView.setAdapter(ingredientsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		requireActivity().setTitle(viewModel.getRecipeName());
		considerInitializingMasterDetailFlow(firstTime);
	}

	private void onClickAddWidget() {
		viewModel.addWidget(requireContext());
	}

	private boolean isTablet() {
		return requireContext().getResources().getBoolean(R.bool.isTablet);
	}

	@Override
	protected void observeEmittedStateChanges() {
		viewModel.observeExoplayerStateChanges(this, getViewLifecycleOwner());
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViewModel();
		initializeViews(savedInstanceState == null);
		observeEmittedStateChanges();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.steps_overview_fragment, container, false);
		return binding.getRoot();
	}

	@Override
	public void onEmittedStateChange(int exoPlayerState) {
		if (exoPlayerState == ExoPlayerState.MAXIMIZED) {
			binding.ingredientsRecyclerView.setVisibility(GONE);
			binding.stepsRecyclerView.setVisibility(GONE);
			binding.addWidgetButton.setVisibility(GONE);
			binding.ingredientsTitle.setVisibility(GONE);
			binding.stepsTitle.setVisibility(GONE);
			return;
		}
		binding.ingredientsRecyclerView.setVisibility(VISIBLE);
		binding.stepsRecyclerView.setVisibility(VISIBLE);
		binding.addWidgetButton.setVisibility(VISIBLE);
		binding.ingredientsTitle.setVisibility(VISIBLE);
		binding.stepsTitle.setVisibility(VISIBLE);
	}

	private void onWatchClicked(Step step) {
		int index = viewModel.getSteps().indexOf(step);
		viewModel.emitNavigation(index);
		//Could be eliminated with observables
		if (!isTablet()) {
			viewModel.position = index;
			NavHostFragment.findNavController(this).navigate(R.id.stepFragment);
		}
	}

	private void setupViewModel() {
		ViewModelProvider provider = new ViewModelProvider(NavHostFragment.findNavController(this).
				getViewModelStoreOwner(R.id.nav_graph), new DetailViewModelFactory(getRecipeDetailsFromBundle()));
		viewModel = provider.get(DetailViewModel.class);
		viewModel.setRecipe(getRecipeDetailsFromBundle());
	}
}
