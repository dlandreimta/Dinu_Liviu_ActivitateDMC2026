package com.example.laborator04;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DispozitivLaptop implements Parcelable {
    public enum TipEcran { LCD, OLED, AMOLED }

    private String model;
    private int memorieRAM;
    private boolean areTastaturaIluminata;
    private double pret;
    private TipEcran tipEcran;
    private Date dataAchizitie;

    public DispozitivLaptop(String model, int memorieRAM, boolean areTastaturaIluminata, double pret, TipEcran tipEcran, Date dataAchizitie) {
        this.model = model;
        this.memorieRAM = memorieRAM;
        this.areTastaturaIluminata = areTastaturaIluminata;
        this.pret = pret;
        this.tipEcran = tipEcran;
        this.dataAchizitie = dataAchizitie;
    }

    protected DispozitivLaptop(Parcel in) {
        model = in.readString();
        memorieRAM = in.readInt();
        areTastaturaIluminata = in.readByte() != 0; // citim booleanul ca byte
        pret = in.readDouble();
        // Citim Enum-ul din String-ul salvat
        tipEcran = TipEcran.valueOf(in.readString());
        // Citim Data (salvată ca long - timestamp)
        long tmpData = in.readLong();
        dataAchizitie = tmpData == -1 ? null : new Date(tmpData);
    }

    public static final Creator<DispozitivLaptop> CREATOR = new Creator<DispozitivLaptop>() {
        @Override
        public DispozitivLaptop createFromParcel(Parcel in) {
            return new DispozitivLaptop(in);
        }

        @Override
        public DispozitivLaptop[] newArray(int size) {
            return new DispozitivLaptop[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(model);
        dest.writeInt(memorieRAM);
        dest.writeByte((byte) (areTastaturaIluminata ? 1 : 0));
        dest.writeDouble(pret);
        dest.writeString(tipEcran.name()); // Salvăm numele Enum-ului
        dest.writeLong(dataAchizitie != null ? dataAchizitie.getTime() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getMemorieRAM() { return memorieRAM; }
    public void setMemorieRAM(int memorieRAM) { this.memorieRAM = memorieRAM; }

    public boolean isAreTastaturaIluminata() { return areTastaturaIluminata; }
    public void setAreTastaturaIluminata(boolean areTastaturaIluminata) { this.areTastaturaIluminata = areTastaturaIluminata; }

    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    public TipEcran getTipEcran() { return tipEcran; }
    public void setTipEcran(TipEcran tipEcran) { this.tipEcran = tipEcran; }

    public Date getDataAchizitie() { return dataAchizitie; }
    public void setDataAchizitie(Date dataAchizitie) { this.dataAchizitie = dataAchizitie; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataFormata = (dataAchizitie != null) ? sdf.format(dataAchizitie) : "Nespecificată";

        return model + " | RAM: " + memorieRAM + "GB | Data: " + dataFormata;
    }
}