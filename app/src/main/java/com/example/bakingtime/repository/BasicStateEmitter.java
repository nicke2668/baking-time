package com.example.bakingtime.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public class BasicStateEmitter implements StateEmitter {

	private final BasicOnDemandEmittedState exoPlayerState = new BasicOnDemandEmittedState();
	private final BasicOnDemandEmittedState navigationState = new BasicOnDemandEmittedState();

	@Override
	public void addObserver(@NonNull EmittedStateObserver observer, @NonNull LifecycleOwner viewLifecycleOwner) {
		navigationState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		exoPlayerState.observe(viewLifecycleOwner, observer::onEmittedStateChange);

	}

	@Override
	public void emitExoPlayerState(int state) {
		exoPlayerState.startObservingWithValue(state);
	}

	@Override
	public void emitStepSelection(int step) {
		navigationState.startObservingWithValue(step);
	}

	/**
	 * Removes the observers.
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	@Override
	public void removeObservers(@NonNull LifecycleOwner viewLifecycleOwner) {
		navigationState.removeObservers(viewLifecycleOwner);
		exoPlayerState.removeObservers(viewLifecycleOwner);
	}
}