package com.example.laborator04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AddLaptopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_laptop);

        Spinner spRam = findViewById(R.id.spRam);
        Integer[] ramOptions = {8, 16, 32};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ramOptions);
        spRam.setAdapter(adapter);

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

                DispozitivLaptop laptopNou = new DispozitivLaptop(model, ram, keyboard, pret, tip);

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