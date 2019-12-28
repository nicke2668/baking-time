package com.example.bakingtime.view;

import java.util.Objects;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.example.bakingtime.R;
import com.example.bakingtime.databinding.StepFragmentBinding;
import com.example.bakingtime.model.Step;
import com.example.bakingtime.repository.EmittedStateObserver;
import com.example.bakingtime.repository.RecipeContentRepository.ExoPlayerState;
import com.example.bakingtime.viewmodel.DetailViewModel;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.navigation.fragment.NavHostFragment;

import static android.view.View.GONE;

public class StepFragment extends RecipeDetailFragment implements StepNavigationCallback, EmittedStateObserver {

	private StepFragmentBinding binding;
	private SimpleExoPlayer exoPlayer;
	private DetailViewModel viewModel;

	private void considerEnteringFullscreen(@NonNull String videoUrl) {
		if (isTabletAndLandscape() && !videoUrl.isEmpty()) {
			Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
			Objects.requireNonNull(getActivity()).getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
							View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

			maximizeExoPlayer();
		}
	}

	private void handleConfigurationChange(@NonNull Configuration newConfig) {
		releasePlayer();
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			maximizeExoPlayer();
			return;
		}
		minimizeExoPlayer();
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

	private void initializePlayer(Uri mediaUri) {
		if (exoPlayer == null) {
			exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), createTrackSelector());
			binding.playerView.setPlayer(exoPlayer);
		}
		exoPlayer.prepare(createMediaSource(mediaUri));
		exoPlayer.seekTo(viewModel.exoPlayerPreviousPosition);
		exoPlayer.setPlayWhenReady(viewModel.exoPlayerState);
	}

	private void initializePlayerView(String videoUrl) {
		binding.placeholder.setVisibility(GONE);
		binding.playerView.setVisibility(View.VISIBLE);
		initializePlayer(Uri.parse(videoUrl));
	}

	private boolean isTabletAndLandscape() {
		Resources resources = requireContext().getResources();
		return resources.getBoolean(R.bool.isTablet) && resources.getConfiguration().orientation
				== Configuration.ORIENTATION_LANDSCAPE;
	}

	private void considerLoadingThumbnail() {
		binding.placeholder.setVisibility(View.VISIBLE);
		binding.playerView.setVisibility(GONE);
		if (!TextUtils.isEmpty(viewModel.getCurrentStep().getThumbnailUrl())) {
			Picasso.get().load(viewModel.getCurrentStep().getThumbnailUrl())
					.placeholder(R.drawable.placeholder_image)
					.into(binding.placeholder);
			return;
		}
		binding.placeholder.setImageResource(R.drawable.placeholder_image);

	}

	private void maximizeExoPlayer() {
		// TODO: 12/27/2019 use observables to control visibility of other widgets
		binding.stepDescription.setVisibility(GONE);
		binding.stepShortDescription.setVisibility(GONE);
		ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.playerView.getLayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		binding.playerView.setLayoutParams(params);
		viewModel.emitExoPlayerState(ExoPlayerState.MAXIMIZED);

	}

	private void minimizeExoPlayer() {
		// TODO: 12/27/2019 use observables to control visibility of other widgets
		viewModel.emitExoPlayerState(ExoPlayerState.DEFAULT);
		binding.stepDescription.setVisibility(View.VISIBLE);
		binding.stepShortDescription.setVisibility(View.VISIBLE);
		ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) binding.playerView.getLayoutParams();
		params.width = LayoutParams.MATCH_PARENT;
		params.height = 600;
		binding.playerView.setLayoutParams(params);
	}

	@Override
	protected void observeEmittedStateChanges() {
		viewModel.observeNavigationStateChanges(this, getViewLifecycleOwner());
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViewModel();
		populateViews();
		observeEmittedStateChanges();
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		handleConfigurationChange(newConfig);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.step_fragment, container, false);
		return binding.getRoot();
	}

	private boolean isTablet() {
		return requireContext().getResources().getBoolean(R.bool.isTablet);
	}

	@Override
	public void onEmittedStateChange(int state) {
		releasePlayer();
		viewModel.position = state;
		populateViews();
		if (isTablet())
			viewModel.position = 0;
	}

	private void populateViews() {
		Step step = viewModel.getCurrentStep();
		binding.setStep(step);
		binding.setCallback(this);
		binding.stepShortDescription.setText(step.getShortDescription());
		binding.stepDescription.setText(step.getDescription());
		binding.setTotalStepCount(viewModel.getTotalStepCount());
		String videoUrl = step.getVideoUrl();
		considerEnteringFullscreen(videoUrl);
		resetVideo();
		if (!videoUrl.isEmpty()) {
			initializePlayerView(videoUrl);
			return;
		}
		considerLoadingThumbnail();
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

	private void releasePlayer() {
		if (exoPlayer != null) {
			viewModel.exoPlayerPreviousPosition = exoPlayer.getCurrentPosition();
			viewModel.exoPlayerState = exoPlayer.getPlayWhenReady();
			exoPlayer.stop();
			exoPlayer.release();
			exoPlayer = null;
		}
	}

	private void resetVideo() {
		viewModel.exoPlayerState = true;
		viewModel.exoPlayerPreviousPosition = 0;
		if (exoPlayer != null) {
			exoPlayer.stop();
		}
	}

	private void setupViewModel() {
		viewModel = new ViewModelProvider(NavHostFragment.findNavController(this).getViewModelStoreOwner(R.id.nav_graph).getViewModelStore(), AndroidViewModelFactory.getInstance(Objects.requireNonNull(getActivity()).getApplication())).get(DetailViewModel.class);
	}

}
