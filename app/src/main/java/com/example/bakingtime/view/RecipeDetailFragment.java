package com.example.bakingtime.view;

import com.example.bakingtime.repository.RecipeContentRepository;

import androidx.fragment.app.Fragment;

public abstract class RecipeDetailFragment extends Fragment {

	protected abstract void observeEmittedStateChanges();

	@Override
	public void onDestroy() {
		super.onDestroy();
		RecipeContentRepository.INSTANCE.getExoPlayerstateEmitter().removeObservers(this);
		RecipeContentRepository.INSTANCE.getNavigationStateEmitter().removeObservers(this);
	}

}
