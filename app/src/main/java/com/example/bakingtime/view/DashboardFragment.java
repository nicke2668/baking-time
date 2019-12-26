package com.example.bakingtime.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.DashboardFragmentBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.viewmodel.DashboardViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

public class DashboardFragment extends Fragment {

	private final RecipeAdapter adapter = new RecipeAdapter(this::onItemClick);
	private DashboardViewModel viewModel;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DashboardFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.dashboard_fragment, container, false);
		binding.recipesRecyclerView.setAdapter(adapter);
		binding.recipesRecyclerView.setHasFixedSize(true);
		binding.recipesRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
		setupViewModel();
		viewModel.getRecipes().observe(this, adapter::setList);
		requireActivity().setTitle(R.string.appName);
		return binding.getRoot();
	}

	private void onItemClick(Recipe recipe) {
		NavHostFragment.findNavController(this).navigate(DashboardFragmentDirections.dashboardFragmentToOverviewFragment(recipe));
	}

	private void setupViewModel() {
		viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
	}
}
