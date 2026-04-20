package com.example.laborator08;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class LaptopActivity extends AppCompatActivity {

    private EditText etBrand, etModel, etPriceMin, etPriceMax;
    private Button btnInsert, btnSelectAll, btnFilterBrand, btnFilterPrice, btnDeletePrice, btnUpdateLetter;
    private ListView listView;

    private AppDatabase db;
    private ArrayAdapter<DispozitivLaptop> adapter;
    private List<DispozitivLaptop> laptopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        db = AppDatabase.getInstance(this);
        initViews();
        setupAdapter();
        setupListeners();
        loadAllLaptops();
    }

    private void initViews() {
        etBrand = findViewById(R.id.etBrand);
        etModel = findViewById(R.id.etModel);
        etPriceMin = findViewById(R.id.etPriceMin);
        etPriceMax = findViewById(R.id.etPriceMax);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelectAll = findViewById(R.id.btnSelectAll);
        btnFilterBrand = findViewById(R.id.btnFilterBrand);
        btnFilterPrice = findViewById(R.id.btnFilterPrice);
        btnDeletePrice = findViewById(R.id.btnDeletePrice);
        btnUpdateLetter = findViewById(R.id.btnUpdateLetter);
        listView = findViewById(R.id.listView);
    }

    private void setupAdapter() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, laptopList);
        listView.setAdapter(adapter);
    }

    private void setupListeners() {
        btnInsert.setOnClickListener(v -> {
            String brand = etBrand.getText().toString();
            String model = etModel.getText().toString();
            String priceStr = etPriceMin.getText().toString();

            if (!brand.isEmpty() && !priceStr.isEmpty()) {
                try {
                    int price = Integer.parseInt(priceStr);
                    DispozitivLaptop laptop = new DispozitivLaptop(brand, model, price);
                    db.dispozitivLaptopDao().insert(laptop);
                    loadAllLaptops();
                    clearInputs();
                    Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSelectAll.setOnClickListener(v -> loadAllLaptops());

        btnFilterBrand.setOnClickListener(v -> {
            String brand = etBrand.getText().toString();
            if (!brand.isEmpty()) {
                updateList(db.dispozitivLaptopDao().getByBrand(brand));
            } else {
                Toast.makeText(this, "Enter brand name", Toast.LENGTH_SHORT).show();
            }
        });

        btnFilterPrice.setOnClickListener(v -> {
            String minStr = etPriceMin.getText().toString();
            String maxStr = etPriceMax.getText().toString();
            if (!minStr.isEmpty() && !maxStr.isEmpty()) {
                try {
                    int min = Integer.parseInt(minStr);
                    int max = Integer.parseInt(maxStr);
                    updateList(db.dispozitivLaptopDao().getByPriceRange(min, max));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter both min and max prices", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeletePrice.setOnClickListener(v -> {
            String priceStr = etPriceMin.getText().toString();
            if (!priceStr.isEmpty()) {
                try {
                    int limit = Integer.parseInt(priceStr);
                    db.dispozitivLaptopDao().deleteByPriceHigherThan(limit);
                    loadAllLaptops();
                    Toast.makeText(this, "Deleted laptops with price > " + limit, Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdateLetter.setOnClickListener(v -> {
            String input = etBrand.getText().toString();
            if (!input.isEmpty()) {
                String letter = input.substring(0, 1);
                db.dispozitivLaptopDao().incrementPriceByStartLetter(letter);
                loadAllLaptops();
                Toast.makeText(this, "Updated prices for brands starting with " + letter, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter a letter in Brand field", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllLaptops() {
        updateList(db.dispozitivLaptopDao().getAll());
    }

    private void updateList(List<DispozitivLaptop> newList) {
        laptopList.clear();
        laptopList.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    private void clearInputs() {
        etBrand.setText("");
        etModel.setText("");
        etPriceMin.setText("");
        etPriceMax.setText("");
    }
}
