package com.example.bakingtime.persistence;

import com.example.bakingtime.repository.RecipeDatabase;

public enum RecipeDatabaseValueHolder implements ChangeableValueHolder<RecipeDatabase> {
	INSTANCE {
		private transient RecipeDatabase database;

		@Override
		public RecipeDatabase getValue() {
			return database;
		}

		@Override
		public void setValue(RecipeDatabase value) {
			this.database = value;
		}
	}
}
