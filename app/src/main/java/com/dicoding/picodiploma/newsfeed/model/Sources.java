package com.dicoding.picodiploma.newsfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Sources implements Parcelable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
    }

    private Sources(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Sources> CREATOR = new Parcelable.Creator<Sources>() {
        @Override
        public Sources createFromParcel(Parcel source) {
            return new Sources(source);
        }

        @Override
        public Sources[] newArray(int size) {
            return new Sources[size];
        }
    };
}
