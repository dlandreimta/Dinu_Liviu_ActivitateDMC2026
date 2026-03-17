package com.example.laborator04;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DispozitivLaptop implements Serializable {
    public enum TipEcran { LCD, OLED, AMOLED }

    private String model;
    private int memorieRAM;
    private boolean areTastaturaIluminata;
    private double pret;
    private TipEcran tipEcran;
    private Date dataAchizitie; // lab5 atribut date

    public DispozitivLaptop(String model, int memorieRAM, boolean areTastaturaIluminata, double pret, TipEcran tipEcran, Date dataAchizitie) {
        this.model = model;
        this.memorieRAM = memorieRAM;
        this.areTastaturaIluminata = areTastaturaIluminata;
        this.pret = pret;
        this.tipEcran = tipEcran;
        this.dataAchizitie = dataAchizitie;
    }

    public String getModel() { return model; }
    public Date getDataAchizitie() { return dataAchizitie; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dataFormata = (dataAchizitie != null) ? sdf.format(dataAchizitie) : "Nespecificată";

        return "💻 " + model + " | RAM: " + memorieRAM + "GB\n" +
                "Ecran: " + tipEcran + " | Iluminat: " + (areTastaturaIluminata ? "Da" : "Nu") + "\n" +
                "Preț: " + pret + " RON | Data: " + dataFormata;
    }
}