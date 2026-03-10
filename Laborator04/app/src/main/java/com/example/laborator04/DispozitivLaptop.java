package com.example.laborator04;
import java.io.Serializable;

public class DispozitivLaptop implements Serializable {
    public enum TipEcran { LCD, OLED, AMOLED }

    private String model;
    private int memorieRAM;
    private boolean areTastaturaIluminata;
    private double pret;
    private TipEcran tipEcran;

    public DispozitivLaptop(String model, int memorieRAM, boolean areTastaturaIluminata, double pret, TipEcran tipEcran) {
        this.model = model;
        this.memorieRAM = memorieRAM;
        this.areTastaturaIluminata = areTastaturaIluminata;
        this.pret = pret;
        this.tipEcran = tipEcran;
    }

    @Override
    public String toString() {
        return "Laptop: " + model + "\nRAM: " + memorieRAM + " GB\nIluminat: " + (areTastaturaIluminata ? "Da" : "Nu") + "\nPret: " + pret + " RON\nEcran: " + tipEcran;
    }
}