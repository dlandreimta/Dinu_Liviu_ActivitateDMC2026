package com.example.laborator03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {
    int n1, n2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btnBack = findViewById(R.id.buttonInapoi);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            n1 = extras.getInt("numar1");
            n2 = extras.getInt("numar2");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sumaCalculata = n1 + n2;

                Intent intentInapoi = new Intent();
                intentInapoi.putExtra("mesaj_retur", "Suma celor două valori este: ");
                intentInapoi.putExtra("valoare_suma", sumaCalculata);

                setResult(RESULT_OK, intentInapoi);

                finish();
            }
        });
    }
}