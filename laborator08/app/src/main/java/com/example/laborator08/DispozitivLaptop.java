package com.example.laborator08;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "laptops")
public class DispozitivLaptop {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "brand")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "price")
    private int price;

    public DispozitivLaptop(@NonNull String brand, String model, int price) {
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getBrand() { return brand; }
    public void setBrand(@NonNull String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + brand + " " + model + " | Price: " + price;
    }
}
