package com.example.laborator04;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DispozitivLaptop implements Parcelable {
    private final String model;
    private final int memorieRAM;
    private final boolean areTastaturaIluminata;
    private final double pret;
    private final String tipEcran;
    private final long dateAsLong;

    public DispozitivLaptop(String model, int memorieRAM, boolean areTastaturaIluminata, double pret, String tipEcran, Date dateAchizitie) {
        this.model = model;
        this.memorieRAM = memorieRAM;
        this.areTastaturaIluminata = areTastaturaIluminata;
        this.pret = pret;
        this.tipEcran = tipEcran;
        this.dateAsLong = (dateAchizitie != null) ? dateAchizitie.getTime() : -1;
    }

    protected DispozitivLaptop(Parcel in) {
        model = in.readString();
        memorieRAM = in.readInt();
        areTastaturaIluminata = in.readByte() != 0;
        pret = in.readDouble();
        tipEcran = in.readString();
        dateAsLong = in.readLong();
    }

    public static final Creator<DispozitivLaptop> CREATOR = new Creator<>() {
        @NonNull
        @Override
        public DispozitivLaptop createFromParcel(@NonNull Parcel in) {
            return new DispozitivLaptop(in);
        }

        @Override
        public DispozitivLaptop[] newArray(int size) {
            return new DispozitivLaptop[size];
        }
    };

    public String toFileString() {
        return model + "," + memorieRAM + "," + areTastaturaIluminata + "," + pret + "," + tipEcran + "," + dateAsLong;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeInt(memorieRAM);
        dest.writeByte((byte) (areTastaturaIluminata ? 1 : 0));
        dest.writeDouble(pret);
        dest.writeString(tipEcran);
        dest.writeLong(dateAsLong);
    }

    @Override
    public int describeContents() { return 0; }

    public String getModel() { return model; }
    public int getMemorieRAM() { return memorieRAM; }
    public boolean isAreTastaturaIluminata() { return areTastaturaIluminata; }
    public double getPret() { return pret; }
    public String getTipEcran() { return tipEcran; }
    public Date getDataAchizitie() { return (dateAsLong != -1) ? new Date(dateAsLong) : null; }

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateStr = (dateAsLong != -1) ? sdf.format(new Date(dateAsLong)) : "N/A";
        return model + " (" + memorieRAM + "GB) - " + dateStr;
    }
}