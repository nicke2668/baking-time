package com.example.bakingtime.view;

import java.util.Objects;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.RecipeStepFragmentBinding;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.viewmodel.DetailViewModel;
import com.example.bakingtime.viewmodel.DetailViewModelFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class RecipeStepFragment extends Fragment implements StepNavigationCallback {

	private RecipeStepFragmentBinding binding;
	private SimpleExoPlayer exoPlayer;
	private DetailViewModel viewModel;

	private void considerLoadingThumbnail() {
		binding.imageViewPlayer.setVisibility(View.VISIBLE);
		binding.playerView.setVisibility(View.GONE);
		if (!TextUtils.isEmpty(viewModel.currentStep.getThumbnailUrl())) {
			Picasso.get().load(viewModel.currentStep.getThumbnailUrl())
					.placeholder(R.drawable.placeholder)
					.into(binding.imageViewPlayer);
			return;
		}
		binding.imageViewPlayer.setImageResource(R.drawable.placeholder);
	}

	@NonNull
	private MediaSource createMediaSource(Uri mediaUri) {
		DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(requireContext(),
				Util.getUserAgent(requireContext(), getString(R.string.appName)));
		return new ExtractorMediaSource.Factory(dataSourceFactory)
				.createMediaSource(mediaUri);
	}

	@NonNull
	private TrackSelector createTrackSelector() {
		return new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter()));
	}

	private Recipe getRecipeDetailsFromBundle() {
		return RecipeStepFragmentArgs.fromBundle(requireArguments()).getRecipe();
	}

	private void initializePlayer(Uri mediaUri) {
		if (exoPlayer == null) {
			exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), createTrackSelector());
			binding.playerView.setPlayer(exoPlayer);
		}
		exoPlayer.prepare(createMediaSource(mediaUri));
		exoPlayer.seekTo(viewModel.previousPosition);
		exoPlayer.setPlayWhenReady(viewModel.playState);
	}

	private void initializePlayerView(String videoUrl) {
		binding.imageViewPlayer.setVisibility(View.GONE);
		binding.playerView.setVisibility(View.VISIBLE);
		initializePlayer(Uri.parse(videoUrl));
	}

	private boolean isPhoneAndLandscape() {
		return requireContext().getResources().getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE
				&& requireContext().getResources().getConfiguration().smallestScreenWidthDp < 600;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel.stepNumber = -1;
		if (isPhoneAndLandscape()) {
			Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
			Objects.requireNonNull(getActivity()).getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
							View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
		populateViews();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setupViewModel();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.recipe_step_fragment, container, false);
		binding.setCallback(this);
		return binding.getRoot();
	}

	@Override
	public void onNextClicked() {
		viewModel.onNextStepClicked();
		populateViews();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			requireActivity().onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		releasePlayer();
	}

	@Override
	public void onPreviousClicked() {
		viewModel.onPreviousClicked();
		populateViews();
	}

	private void populateViews() {
		resetVideo();
		binding.setTotalStepCount(viewModel.getTotalStepCount());
		binding.setStep(viewModel.currentStep);
		String videoUrl = viewModel.currentStep.getVideoUrl();
		if (!videoUrl.isEmpty()) {
			initializePlayerView(videoUrl);
			return;
		}
		considerLoadingThumbnail();
	}

	private void releasePlayer() {
		if (exoPlayer != null) {
			viewModel.previousPosition = exoPlayer.getCurrentPosition();
			viewModel.playState = exoPlayer.getPlayWhenReady();
			exoPlayer.stop();
			exoPlayer.release();
			exoPlayer = null;
		}
	}

	private void resetVideo() {
		viewModel.playState = true;
		viewModel.previousPosition = 0;
		if (exoPlayer != null) {
			exoPlayer.stop();
		}
	}

	private void setupViewModel() {
		viewModel = ViewModelProviders.of(this, new DetailViewModelFactory(getRecipeDetailsFromBundle())).get(DetailViewModel.class);
	}

}
