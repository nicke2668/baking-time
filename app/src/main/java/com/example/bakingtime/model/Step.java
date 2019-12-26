package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Step implements Parcelable {

	public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {

		public Step createFromParcel(Parcel in) {
			return new Step(in);
		}

		public Step[] newArray(int size) {
			return new Step[size];
		}
	};
	private String description;
	private int id;
	@PrimaryKey(autoGenerate = true)
	private int roomId;
	private String shortDescription;
	private int stepId;
	@SerializedName("thumbnailURL")
	private String thumbnailUrl;
	@SerializedName("videoURL")
	private String videoUrl;

	public Step() {
	}

	public Step(Parcel in) {
		id = in.readInt();
		description = in.readString();
		shortDescription = in.readString();
		thumbnailUrl = in.readString();
		videoUrl = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public int getRoomId() {
		return roomId;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public int getStepId() {
		return stepId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public void setStepId(int StepId) {
		this.stepId = StepId;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(description);
		dest.writeInt(id);
		dest.writeString(shortDescription);
		dest.writeString(thumbnailUrl);
		dest.writeString(videoUrl);

	}
}