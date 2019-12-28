package com.example.bakingtime.repository;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

class BasicOnDemandEmittedState extends LiveData<Integer> {

	private final AtomicBoolean canObserve = new AtomicBoolean(false);

	@Override
	public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Integer> observer) {
		super.observe(owner, value -> {
			if (canObserve.compareAndSet(true, false)) {
				observer.onChanged(value);
			}
		});
	}

	void startObservingWithValue(@NonNull Integer value) {
		canObserve.set(true);
		super.postValue(value);
	}
}
