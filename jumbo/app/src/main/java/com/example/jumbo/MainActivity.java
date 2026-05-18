package com.example.jumbo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private android.widget.ListView lvFilms;
    private java.util.List<Film> filmList = new ArrayList<>();
    private FilmAdapter filmAdapter;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> addFilmLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvFilms = findViewById(R.id.lvFilms);
        filmAdapter = new FilmAdapter(this, R.layout.item_film, filmList);
        lvFilms.setAdapter(filmAdapter);

        loadFromFile();

        addFilmLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                (androidx.activity.result.ActivityResult result) -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Film film = result.getData().getParcelableExtra("film_key");
                        if (film != null) {
                            filmList.add(film);
                            filmAdapter.notifyDataSetChanged();

                            saveOnFile();
                        }
                    }
                }
        );


        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                addFilmLauncher.launch(new android.content.Intent(MainActivity.this, AddFilmActivity.class));
            }
        });
    }

    private void saveOnFile() {
        try {
            java.io.FileOutputStream fos = openFileOutput("films.txt", android.content.Context.MODE_PRIVATE);
            java.io.PrintWriter writer = new java.io.PrintWriter(fos);

            for (Film film : filmList) {
                // Scriem manual cu separatorul "|"
                writer.println(film.getTitle() + "|" +
                        film.getGenre() + "|" +
                        film.getYear() + "|" +
                        film.is4k() + "|" +
                        film.getRating());
            }
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try {
            java.io.FileInputStream fis = openFileInput("films.txt");
            java.util.Scanner scanner = new java.util.Scanner(fis);

            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                if (linie.trim().isEmpty()) continue; // Sărim peste liniile goale

                String[] bucati = linie.split("\\|");

                // Verificare
                if (bucati.length == 5) {
                    try {
                        Film f = new Film(
                                bucati[0],
                                bucati[1],
                                Integer.parseInt(bucati[2].trim()),
                                Boolean.parseBoolean(bucati[3].trim()),
                                Float.parseFloat(bucati[4].trim())
                        );
                        filmList.add(f);
                    } catch (Exception e) {
                        // Dacă o linie e stricată (ex: text în loc de an), o sărim doar pe ea
                        android.util.Log.e("EROARE_FILM", "Linie coruptă: " + linie);
                    }
                }
            }
            scanner.close();

            // FOARTE IMPORTANT: Verificăm să nu fie null adaptorul
            if (filmAdapter != null) {
                filmAdapter.notifyDataSetChanged();
            }
        } catch (java.io.FileNotFoundException e) {
            // E ok, fișierul nu există încă la prima rulare
        }
    }
}
