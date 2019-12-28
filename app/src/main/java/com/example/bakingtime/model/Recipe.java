package com.example.bakingtime.model;

import java.util.List;
import java.util.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
/**
 * Model object for a recipe.
 *
 * @author Nick Emerson
 */
public class Recipe implements Parcelable {

	public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {

		public Recipe createFromParcel(Parcel in) {
			return new Recipe(in);
		}

		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};
	@PrimaryKey
	private int id;
	private String image;
	@ColumnInfo
	@TypeConverters(DataTypeConverter.class)
	private List<Ingredient> ingredients;
	private String name;
	private int servings;
	@ColumnInfo
	@TypeConverters(DataTypeConverter.class)
	private List<Step> steps;

	public Recipe() {
	}

	private Recipe(Parcel in) {
		id = in.readInt();
		image = in.readString();
		ingredients = in.readArrayList(Ingredient.class.getClassLoader());
		name = in.readString();
		servings = in.readInt();
		steps = in.readParcelable(Step.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		return Objects.equals(((Recipe) obj).getId(), id);
	}

	public int getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public String getName() {
		return name;
	}

	public int getServings() {
		return servings;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(image);
		dest.writeList(ingredients);
		dest.writeString(name);
		dest.writeInt(servings);
		dest.writeList(steps);
	}
}
