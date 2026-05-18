package com.example.c114b_dinu_liviu;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Eveniment implements Parcelable {
    private String denumire;
    private String locatie;
    private String data;
    private String descriere;
    private boolean sensibil;

    public Eveniment(String denumire, String locatie, String data, String descriere, boolean sensibil) {
        this.denumire = denumire;
        this.locatie = locatie;
        this.data = data;
        this.descriere = descriere;
        this.sensibil = sensibil;
    }

    protected Eveniment(Parcel in) {
        denumire = in.readString();
        locatie = in.readString();
        data = in.readString();
        descriere = in.readString();
        sensibil = in.readByte() != 0;
    }

    public static final Creator<Eveniment> CREATOR = new Creator<Eveniment>() {
        @Override
        public Eveniment createFromParcel(Parcel in) {
            return new Eveniment(in);
        }

        @Override
        public Eveniment[] newArray(int size) {
            return new Eveniment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeString(locatie);
        dest.writeString(data);
        dest.writeString(descriere);
        dest.writeByte((byte) (sensibil ? 1 : 0));
    }


    public String getDenumire() {
        return denumire;
    }

    public String getLocatie() {
        return locatie;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getDescriere() {
        return descriere;
    }

    public boolean isSensibil() {
        return sensibil;
    }

    @Override
    public String toString() {
        return "Eveniment: " + denumire + ", Locatie: " + locatie + ", Data: " + data + ", Descriere: " + descriere + ", Sensibil: " + sensibil;
    }
}
