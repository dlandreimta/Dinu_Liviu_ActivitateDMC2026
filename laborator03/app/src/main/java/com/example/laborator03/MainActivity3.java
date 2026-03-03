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
    int n1, n2; // Variabilele primite

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button btnBack = findViewById(R.id.buttonInapoi); // Asigură-te că ai ID-ul ăsta în XML

        // Preluăm datele primite (Pasul 9)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            n1 = extras.getInt("numar1");
            n2 = extras.getInt("numar2");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pasul 10: Calculăm suma $$Suma = n1 + n2$$
                int sumaCalculata = n1 + n2;

                // Creăm un Intent "gol" doar pentru a transporta datele înapoi
                Intent intentInapoi = new Intent();
                intentInapoi.putExtra("mesaj_retur", "Suma celor două valori este: ");
                intentInapoi.putExtra("valoare_suma", sumaCalculata);

                // Setezi rezultatul ca fiind OK și atașezi datele
                setResult(RESULT_OK, intentInapoi);

                // Închizi activitatea curentă (te întorci automat la MA2)
                finish();
            }
        });
    }
}