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

        Spinner spRam = findViewById(R.id.spRam);
        Integer[] ramOptions = {8, 16, 32, 64};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ramOptions);
        spRam.setAdapter(adapter);

        EditText etDate = findViewById(R.id.etDate);
        etDate.setText(sdf.format(dataSelectata));

        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int an = calendar.get(Calendar.YEAR);
            int luna = calendar.get(Calendar.MONTH);
            int zi = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog picker = new DatePickerDialog(AddLaptopActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(year, month, dayOfMonth);
                        dataSelectata = calendar.getTime();
                        etDate.setText(sdf.format(dataSelectata));
                    }, an, luna, zi);
            picker.show();
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etModel = findViewById(R.id.etModel);
                Switch swKeyboard = findViewById(R.id.swKeyboard);
                RadioGroup rgScreen = findViewById(R.id.rgScreenType);
                RatingBar rbPrice = findViewById(R.id.rbPrice);

                String model = etModel.getText().toString();
                int ram = (int) spRam.getSelectedItem();
                boolean keyboard = swKeyboard.isChecked();
                double pret = rbPrice.getRating() * 1000.0;

                DispozitivLaptop.TipEcran tip = DispozitivLaptop.TipEcran.LCD;
                if (rgScreen.getCheckedRadioButtonId() == R.id.rbOled) tip = DispozitivLaptop.TipEcran.OLED;
                if (rgScreen.getCheckedRadioButtonId() == R.id.rbAmoled) tip = DispozitivLaptop.TipEcran.AMOLED;

                DispozitivLaptop laptopNou = new DispozitivLaptop(model, ram, keyboard, pret, tip, dataSelectata);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("laptop_obiect", laptopNou);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}