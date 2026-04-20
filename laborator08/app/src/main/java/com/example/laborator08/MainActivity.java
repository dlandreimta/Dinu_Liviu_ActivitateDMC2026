package com.example.laborator08;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToRoom = findViewById(R.id.btnGoToRoom);
        Button btnGoToImages = findViewById(R.id.btnGoToImages);

        btnGoToRoom.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
            startActivity(intent);
        });

        btnGoToImages.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImageActivity.class);
            startActivity(intent);
        });
    }
}
