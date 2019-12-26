package com.example.bakingtime;

import android.app.Application;

import com.example.bakingtime.persistence.RecipeDatabaseValueHolder;
import com.example.bakingtime.repository.RecipeDatabase;

import androidx.room.Room;
import timber.log.Timber;

public class BakingTimeApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		RecipeDatabaseValueHolder.INSTANCE.setValue(Room.databaseBuilder(this, RecipeDatabase.class, "RecipeDatabase").build());
	}
}
