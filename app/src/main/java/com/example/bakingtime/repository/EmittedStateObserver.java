package com.example.bakingtime.repository;

import androidx.lifecycle.LifecycleOwner;

/**
 * A listener able to handle the emitted state change from a live data instance.
 *
 */
public interface EmittedStateObserver extends LifecycleOwner {

	default void onEmittedStateChange(int state) {
		//Do nothing by default
	}

}