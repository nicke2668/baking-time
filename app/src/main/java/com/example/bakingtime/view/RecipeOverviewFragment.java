package com.example.bakingtime.view;

import java.util.Objects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.RecipeOverviewFragmentBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.viewmodel.DetailViewModel;
import com.example.bakingtime.viewmodel.DetailViewModelFactory;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class RecipeOverviewFragment extends Fragment {

	private final IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
	private DetailViewModel viewModel;
	private final StepsAdapter stepsAdapter = new StepsAdapter(this::onWatchClicked);

	private RecipeOverviewFragmentBinding displayMasterDetailLayout(LayoutInflater inflater, ViewGroup container) {
		RecipeOverviewFragmentBinding binding = DataBindingUtil.
				inflate(inflater, R.layout.recipe_overview_fragment, container, false);
		binding.setCallback(this::onClickAddWidget);
		stepsAdapter.setList(viewModel.recipe.getSteps());
		binding.stepsRecyclerView.setAdapter(stepsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		ingredientsAdapter.setList(viewModel.recipe.getIngredients());
		binding.ingredientsRecyclerView.setAdapter(ingredientsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		requireActivity().setTitle(viewModel.recipe.getName());

//		FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//		RecipeStepFragment stepFragment = new RecipeStepFragment();
//		transaction.add(R.id.stepFragmentPlaceholder, stepFragment);
//		transaction.addToBackStack("B");
//		transaction.commit();

		((NavHostFragment) Objects.requireNonNull(getChildFragmentManager().findFragmentById(R.id.stepFragmentContainer))).
				getNavController().navigate(RecipeOverviewFragmentDirections.overviewFragmentToStepFragment(viewModel.recipe));
		return binding;
	}

	@NonNull
	private RecipeOverviewFragmentBinding displaySingleLayout(@NonNull LayoutInflater inflater, ViewGroup container) {
		RecipeOverviewFragmentBinding binding = DataBindingUtil.
				inflate(inflater, R.layout.recipe_overview_fragment, container, false);
		binding.setCallback(this::onClickAddWidget);
		stepsAdapter.setList(viewModel.recipe.getSteps());
		binding.stepsRecyclerView.setAdapter(stepsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		ingredientsAdapter.setList(viewModel.recipe.getIngredients());
		binding.ingredientsRecyclerView.setAdapter(ingredientsAdapter);
		binding.ingredientsRecyclerView.setHasFixedSize(true);
		requireActivity().setTitle(viewModel.recipe.getName());
		return binding;
	}

	private Recipe getRecipeDetailsFromBundle() {
		return RecipeOverviewFragmentArgs.fromBundle(requireArguments()).getRecipe();
	}

	private void onClickAddWidget() {
		viewModel.addWidget(requireContext());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupViewModel();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final boolean isTablet = requireContext().getResources().getBoolean(R.bool.isTablet);
		return isTablet ? displayMasterDetailLayout(inflater, container).getRoot() : displaySingleLayout(inflater, container).getRoot();
	}

	private void onWatchClicked() {
		NavHostFragment.findNavController(this).navigate(RecipeOverviewFragmentDirections.overviewFragmentToStepFragment(viewModel.recipe));

	}

	private void setupViewModel() {
		viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getRecipeDetailsFromBundle())).get(DetailViewModel.class);
	}

}
