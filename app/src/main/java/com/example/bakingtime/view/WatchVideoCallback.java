package com.example.bakingtime.view;

import com.example.bakingtime.model.Step;

import androidx.annotation.NonNull;

public interface WatchVideoCallback {

	void onWatchClicked(@NonNull Step step);
}
