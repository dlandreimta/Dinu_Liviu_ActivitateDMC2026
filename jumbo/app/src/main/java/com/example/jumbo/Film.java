package com.example.jumbo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Film implements Parcelable {
    private String title;
    private String genre;
    private int year;
    private boolean is4k;
    private float rating;

    public Film(String title, String genre, int year, boolean is4k, float rating){
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.is4k = is4k;
        this.rating = rating;
    }

    protected Film(Parcel in) {
        title = in.readString();
        genre = in.readString();
        year = in.readInt();
        is4k = in.readByte() != 0;
        rating = in.readFloat();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(genre);
        dest.writeInt(year);
        dest.writeByte((byte) (is4k ? 1 : 0));
        dest.writeFloat(rating);
    }

    public String toCSV() {
        return title + ',' + genre + ',' + year + ',' + is4k + ',' + rating + '\n';
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public boolean is4k() {
        return is4k;
    }

    public float getRating() {
        return rating;
    }
}
