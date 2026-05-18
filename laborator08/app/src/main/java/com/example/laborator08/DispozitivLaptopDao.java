package com.example.laborator08;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface DispozitivLaptopDao {

    @Insert
    void insert(DispozitivLaptop laptop);

    @Query("SELECT * FROM laptops")
    List<DispozitivLaptop> getAll();

    @Query("SELECT * FROM laptops WHERE brand = :brandName")
    List<DispozitivLaptop> getByBrand(String brandName);

    @Query("SELECT * FROM laptops WHERE price BETWEEN :minPrice AND :maxPrice")
    List<DispozitivLaptop> getByPriceRange(int minPrice, int maxPrice);

    @Query("DELETE FROM laptops WHERE price > :priceLimit")
    void deleteByPriceHigherThan(int priceLimit);

    @Query("UPDATE laptops SET price = price + 1 WHERE brand LIKE :letter || '%'")
    void incrementPriceByStartLetter(String letter);
}
