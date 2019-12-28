package com.example.bakingtime.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

public interface StateEmitter {

	/**
	 * Add's this observer to each of the emitted state change streams held by this emitter.
	 * Implementations should ensure that each emitted state change is accessible only when created.
	 * @see <a href="https://proandroiddev.com/5-common-mistakes-when-using-architecture-components-403e9899f4cb">Possible Solutions</a>
	 *
	 * @param observer to be added to all streams of emitted states
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	void addObserver(@NonNull EmittedStateObserver observer, @NonNull LifecycleOwner viewLifecycleOwner);

	default void emitExoPlayerState(int state) {
		// do nothing by default but implementations may override
	}

	default void emitStepSelection(int step) {
		// do nothing by default but implementations may override
	}

	/**
	 * Removes the observers.
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	void removeObservers(@NonNull LifecycleOwner viewLifecycleOwner);
}