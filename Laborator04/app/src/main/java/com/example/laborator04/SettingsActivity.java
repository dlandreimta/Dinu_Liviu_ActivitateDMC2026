package com.example.laborator04;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText etSize = findViewById(R.id.etFontSize);
        RadioGroup rgColors = findViewById(R.id.rgColors);
        RadioGroup rgBg = findViewById(R.id.rgBackground);
        Button btnSave = findViewById(R.id.btnSaveSettings);

        SharedPreferences sp = getSharedPreferences("SetariApp", MODE_PRIVATE);
        etSize.setText(String.valueOf(sp.getInt("dimensiune_text", 18)));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor ed = sp.edit();

            String valStr = etSize.getText().toString();
            int size = valStr.isEmpty() ? 18 : Integer.parseInt(valStr);
            ed.putInt("dimensiune_text", size);

            String color = "#1A1C1E"; // Default Negru
            int colorId = rgColors.getCheckedRadioButtonId();
            if (colorId == R.id.rbBlue) color = "#2196F3";
            else if (colorId == R.id.rbRed) color = "#F44336";
            ed.putString("culoare_text", color);

            String bg = "#FDFDFD"; // Default Alb
            int bgId = rgBg.getCheckedRadioButtonId();
            if (bgId == R.id.rbBgGrey) bg = "#E0E0E0";
            ed.putString("culoare_fundal", bg);

            ed.apply();
            finish();
        });
    }
}