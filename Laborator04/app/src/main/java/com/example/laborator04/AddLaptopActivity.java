package com.example.laborator04;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddLaptopActivity extends AppCompatActivity {

    private Date dataSelectata = new Date(); // Data implicită este "azi"
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laptop);

        // 1. Identificăm toate componentele vizuale
        EditText etModel = findViewById(R.id.etModel);
        EditText etDate = findViewById(R.id.etDate);
        Spinner spRam = findViewById(R.id.spRam);
        Switch swKeyboard = findViewById(R.id.swKeyboard);
        RadioGroup rgScreen = findViewById(R.id.rgScreenType);
        RatingBar rbPrice = findViewById(R.id.rbPrice);
        Button btnSave = findViewById(R.id.btnSave);

        // 2. Configurare Spinner RAM
        Integer[] ramOptions = {8, 16, 32, 64};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ramOptions);
        spRam.setAdapter(adapter);

        // 3. LOGICA DE EDITARE: Verificăm dacă am primit un obiect pentru modificare
        // Folosim cheia "laptop_edit" pe care am setat-o în DefaultActivity
        DispozitivLaptop laptopDeEditat = getIntent().getParcelableExtra("laptop_edit");

        if (laptopDeEditat != null) {
            // Dacă există, "umplem" formularul cu datele lui
            etModel.setText(laptopDeEditat.getModel());
            swKeyboard.setChecked(laptopDeEditat.isAreTastaturaIluminata());
            rbPrice.setRating((float) (laptopDeEditat.getPret() / 1000.0));

            // Selectăm RAM-ul corect în Spinner
            for (int i = 0; i < ramOptions.length; i++) {
                if (ramOptions[i] == laptopDeEditat.getMemorieRAM()) {
                    spRam.setSelection(i);
                    break;
                }
            }

            // Selectăm tipul de ecran corect
            if (laptopDeEditat.getTipEcran() == DispozitivLaptop.TipEcran.OLED) rgScreen.check(R.id.rbOled);
            else if (laptopDeEditat.getTipEcran() == DispozitivLaptop.TipEcran.AMOLED) rgScreen.check(R.id.rbAmoled);
            else rgScreen.check(R.id.rbLcd);

            // Setăm data salvată
            dataSelectata = laptopDeEditat.getDataAchizitie();
        }

        // Afișăm data (fie cea curentă, fie cea a laptopului de editat)
        etDate.setText(sdf.format(dataSelectata));

        // 4. Configurare Calendar (DatePicker)
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataSelectata); // Pornim calendarul de la data deja selectată

            new DatePickerDialog(AddLaptopActivity.this, (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                dataSelectata = calendar.getTime();
                etDate.setText(sdf.format(dataSelectata));
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // 5. Salvarea datelor
        btnSave.setOnClickListener(v -> {
            String model = etModel.getText().toString();
            int ram = (int) spRam.getSelectedItem();
            boolean keyboard = swKeyboard.isChecked();
            double pret = rbPrice.getRating() * 1000.0;

            DispozitivLaptop.TipEcran tip = DispozitivLaptop.TipEcran.LCD;
            int selectedId = rgScreen.getCheckedRadioButtonId();
            if (selectedId == R.id.rbOled) tip = DispozitivLaptop.TipEcran.OLED;
            else if (selectedId == R.id.rbAmoled) tip = DispozitivLaptop.TipEcran.AMOLED;

            DispozitivLaptop laptopRezultat = new DispozitivLaptop(model, ram, keyboard, pret, tip, dataSelectata);

            Intent intent = new Intent();
            intent.putExtra("laptop_obiect", laptopRezultat);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}