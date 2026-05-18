package com.example.laborator04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DefaultActivity extends AppCompatActivity {

    private static final String TAG = "DefaultActivity";
    private final List<DispozitivLaptop> laptopList = new ArrayList<>();
    private LaptopAdapter adapter;
    private int selectedPos = -1;


    private final ActivityResultLauncher<Intent> addLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    DispozitivLaptop l = result.getData().getParcelableExtra("laptop_obiect");
                    if (l != null) {
                        if (selectedPos == -1) {
                            laptopList.add(l);
                        } else {
                            laptopList.set(selectedPos, l);
                            selectedPos = -1;
                        }
                        adapter.notifyDataSetChanged();

                        salveazaTotInFisier();
                    }
                }
            }
    );

    private void salveazaTotInFisier() {
        try (FileOutputStream fos = openFileOutput("laptops.txt", MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {

            for (DispozitivLaptop l : laptopList) {
                osw.write(l.toFileString() + "\n");
            }
            Log.d(TAG, "Fișierul a fost sincronizat cu succes.");

        } catch (Exception e) {
            Log.e(TAG, "Eroare la sincronizarea fișierului", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView lv = findViewById(R.id.lvLaptops);
        adapter = new LaptopAdapter(this, R.layout.item_laptop, laptopList);
        lv.setAdapter(adapter);

        incarcaDinFisier();

        lv.setOnItemClickListener((p, v, pos, id) -> {
            selectedPos = pos;
            Intent i = new Intent(this, AddLaptopActivity.class);
            i.putExtra("laptop_edit", laptopList.get(pos));
            addLauncher.launch(i);
        });

        lv.setOnItemLongClickListener((p, v, pos, id) -> {
            salveazaInFavorite(laptopList.get(pos));
            return true;
        });

        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            selectedPos = -1;
            addLauncher.launch(new Intent(this, AddLaptopActivity.class));
        });
    }

    private void incarcaDinFisier() {
        try (FileInputStream fis = openFileInput("laptops.txt");
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(isr)) {

            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] tokens = linie.split(",");
                if (tokens.length == 6) {
                    DispozitivLaptop l = new DispozitivLaptop(
                            tokens[0],                      // model
                            Integer.parseInt(tokens[1]),    // ram
                            Boolean.parseBoolean(tokens[2]),// keyboard
                            Double.parseDouble(tokens[3]),  // pret
                            tokens[4],                      // tip
                            new Date(Long.parseLong(tokens[5])) // data
                    );
                    laptopList.add(l);
                }
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, "Fisierul nu exista sau e gol");
        }
    }

    private void salveazaInFavorite(DispozitivLaptop laptop) {
        try (FileOutputStream fos = openFileOutput("favorite.txt", MODE_APPEND);
             OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            osw.write(laptop.toFileString() + "\n");
            Toast.makeText(this, "Adaugat la favorite!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Eroare favorite", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("SetariApp", MODE_PRIVATE);
        String bg = sp.getString("culoare_fundal", "#FDFDFD");
        findViewById(R.id.main_layout).setBackgroundColor(Color.parseColor(bg));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}