package com.example.jumbo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddFilmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_film);

        android.widget.EditText etTitle = findViewById(R.id.etTitle);
        android.widget.Spinner spGenre = findViewById(R.id.spGenre);
        android.widget.EditText etYear = findViewById(R.id.etYear);
        android.widget.CheckBox cb4K = findViewById(R.id.cb4K);

        android.widget.RatingBar rbRating = findViewById(R.id.rbRating);
        android.widget.Button btnSave = findViewById(R.id.btnSave);

        String[] genres = {"Action", "Comedy", "Drama", "Horror", "Romance"};

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genres);
        spGenre.setAdapter(adapter);

        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String title = etTitle.getText().toString();
                String genre = spGenre.getSelectedItem().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                boolean is4k = cb4K.isChecked();
                float rating = rbRating.getRating();

                Film film = new Film(title, genre, year, is4k, rating);

                android.content.Intent intent = new android.content.Intent();
                intent.putExtra("film_key", film);
                setResult(android.app.Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}