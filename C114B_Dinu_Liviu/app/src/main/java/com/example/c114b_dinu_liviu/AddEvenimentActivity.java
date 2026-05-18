package com.example.c114b_dinu_liviu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AddEvenimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_eveniment);

        android.widget.EditText denumireEditText = findViewById(R.id.dlDenumireET);
        android.widget.EditText locatieEditText = findViewById(R.id.dlLocatieET);
        android.widget.DatePicker dataDatePicker = findViewById(R.id.dldataetd);
        android.widget.EditText descriereEditText = findViewById(R.id.dlDescriereET);
        android.widget.CheckBox sensibilCheckBox = findViewById(R.id.dlsensCheckBox);

        if (denumireEditText == null) {
            return;
        }

        if (locatieEditText == null) {
            return;
        }


        android.widget.Button salvareButton = findViewById(R.id.dlbtnSave);

        salvareButton.setOnClickListener(v -> {
            String denumire = denumireEditText.getText().toString();
            String locatie = locatieEditText.getText().toString();
            int an = dataDatePicker.getYear();
            int luna = dataDatePicker.getMonth();
            int zi = dataDatePicker.getDayOfMonth();
            String data = zi + "/" + (luna + 1) + "/" + an;
            String descriere = descriereEditText.getText().toString();
            boolean sensibil = sensibilCheckBox.isChecked();

            if (denumire.isEmpty() || locatie.isEmpty())
            {
                android.widget.Toast.makeText(AddEvenimentActivity.this, "Completati toate campurile", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }

            Eveniment eveniment = new Eveniment(denumire, locatie, data, descriere, sensibil);

            android.content.Intent intent = new android.content.Intent();
            intent.putExtra("eveniment_key", eveniment);
            setResult(android.app.Activity.RESULT_OK, intent);
            finish();
        });


    }
}
