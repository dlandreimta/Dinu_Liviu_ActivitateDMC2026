package com.example.laborator04;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddLaptopActivity extends AppCompatActivity {

    private Date dataSelectata = new Date();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private TextInputEditText etModel, etDate;
    private Spinner spRam;
    private MaterialSwitch cbKeyboard;
    private RadioGroup rgScreen;
    private RatingBar rbPrice;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laptop);

        rootInit();
        aplicaPreferinte();
        setupSpinner();
        loadDataIfPresent();

        if (etDate != null) {
            etDate.setOnClickListener(v -> showCalendar());
        }

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> handleSave());
        }
    }

    private void rootInit() {
        etModel = findViewById(R.id.etModel);
        etDate = findViewById(R.id.etDate);
        spRam = findViewById(R.id.spRam);
        cbKeyboard = findViewById(R.id.cbKeyboard);
        rgScreen = findViewById(R.id.rgScreenType);
        rbPrice = findViewById(R.id.rbPrice);
        btnSave = findViewById(R.id.btnSave);
        if (etDate != null) {
            etDate.setText(sdf.format(dataSelectata));
        }
    }

    private void aplicaPreferinte() {
        SharedPreferences sp = getSharedPreferences("SetariApp", MODE_PRIVATE);
        int size = sp.getInt("dimensiune_text", 18);
        String txtColor = sp.getString("culoare_text", "#1A1C1E");
        String bgColor = sp.getString("culoare_fundal", "#FDFDFD");

        View root = findViewById(R.id.add_root_layout);
        if (root != null) {
            root.setBackgroundColor(Color.parseColor(bgColor));
        }

        if (etModel != null) {
            etModel.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            etModel.setTextColor(Color.parseColor(txtColor));
        }

        if (etDate != null) {
            etDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            etDate.setTextColor(Color.parseColor(txtColor));
        }

        if (btnSave != null) {
            btnSave.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }

    private void loadDataIfPresent() {
        DispozitivLaptop l;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            l = getIntent().getParcelableExtra("laptop_edit", DispozitivLaptop.class);
        } else {
            l = getIntent().getParcelableExtra("laptop_edit");
        }

        if (l != null) {
            if (etModel != null) etModel.setText(l.getModel());
            if (cbKeyboard != null) cbKeyboard.setChecked(l.isAreTastaturaIluminata());
            if (rbPrice != null) rbPrice.setRating((float)(l.getPret() / 1000));

            dataSelectata = l.getDataAchizitie();
            if (etDate != null) etDate.setText(sdf.format(dataSelectata));

            if (rgScreen != null) {
                if ("OLED".equals(l.getTipEcran())) rgScreen.check(R.id.rbOled);
                else if ("AMOLED".equals(l.getTipEcran())) rgScreen.check(R.id.rbAmoled);
                else rgScreen.check(R.id.rbLcd);
            }

            if (spRam != null) {
                for (int i = 0; i < spRam.getCount(); i++) {
                    if (spRam.getItemAtPosition(i).equals(l.getMemorieRAM())) {
                        spRam.setSelection(i);
                        break;
                    }
                }
            }
        }
    }

    private void setupSpinner() {
        Integer[] options = {8, 16, 32, 64};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (spRam != null) {
            spRam.setAdapter(adapter);
        }
    }

    private void showCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(dataSelectata);
        new DatePickerDialog(this, (v, y, m, d) -> {
            c.set(y, m, d);
            dataSelectata = c.getTime();
            if (etDate != null) etDate.setText(sdf.format(dataSelectata));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void handleSave() {
        if (etModel == null || etModel.getText() == null || etModel.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Completează modelul!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tip = "LCD";
        if (rgScreen != null) {
            int selectedId = rgScreen.getCheckedRadioButtonId();
            if (selectedId == R.id.rbOled) tip = "OLED";
            else if (selectedId == R.id.rbAmoled) tip = "AMOLED";
        }

        int ram = (spRam != null) ? (int) spRam.getSelectedItem() : 8;
        boolean keyboard = (cbKeyboard != null) && cbKeyboard.isChecked();
        double pret = (rbPrice != null) ? rbPrice.getRating() * 1000 : 0;

        DispozitivLaptop result = new DispozitivLaptop(
                etModel.getText().toString(),
                ram,
                keyboard,
                pret,
                tip,
                dataSelectata
        );

        Intent i = new Intent();
        i.putExtra("laptop_obiect", result);
        setResult(RESULT_OK, i);
        finish();
    }
}