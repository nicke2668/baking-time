package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.PrimaryKey;

public class Ingredient implements Parcelable {

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
		@Override
		public Ingredient createFromParcel(Parcel in) {
			return new Ingredient(in);
		}

		@Override
		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};
	@SerializedName("ingredient")
	private String name;
	private String measure;
	private double quantity;
	private int recipeId;
	@PrimaryKey(autoGenerate = true)
	private int roomId;

	public Ingredient() {
	}

	protected Ingredient(Parcel in) {
		quantity = in.readDouble();
		measure = in.readString();
		name = in.readString();
		recipeId = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getName() {
		return name;
	}

	public String getMeasure() {
		return measure;
	}

	public double getQuantity() {
		return quantity;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(quantity);
		dest.writeString(measure);
		dest.writeString(name);
		dest.writeInt(recipeId);
	}
}