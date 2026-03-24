package com.example.laborator04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DefaultActivity extends AppCompatActivity {

    private List<DispozitivLaptop> laptopList = new ArrayList<>();
    private LaptopAdapter adapter;
    private ListView lvLaptops;

    private int pozitieEditata = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        lvLaptops = findViewById(R.id.lvLaptops);

        adapter = new LaptopAdapter(this, R.layout.item_laptop, laptopList);
        lvLaptops.setAdapter(adapter);

        lvLaptops.setOnItemClickListener((parent, view, position, id) -> {
            pozitieEditata = position; // Salvăm poziția
            DispozitivLaptop laptopDeEditat = laptopList.get(position);

            Intent intent = new Intent(DefaultActivity.this, AddLaptopActivity.class);
            intent.putExtra("laptop_edit", laptopDeEditat);
            startActivityForResult(intent, 200); // 200 = Cod pentru Editare
        });

        lvLaptops.setOnItemLongClickListener((parent, view, position, id) -> {
            laptopList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Laptop eliminat!", Toast.LENGTH_SHORT).show();
            return true;
        });

        Button btnAdd = findViewById(R.id.btnOpenAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DefaultActivity.this, AddLaptopActivity.class);
            startActivityForResult(intent, 100); // 100 = Cod pentru Adăugare
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            DispozitivLaptop laptopPrimit = data.getParcelableExtra("laptop_obiect");

            if (laptopPrimit != null) {
                if (requestCode == 100) {
                    laptopList.add(laptopPrimit);
                    Toast.makeText(this, "Laptop adăugat!", Toast.LENGTH_SHORT).show();
                }
                else if (requestCode == 200) {
                    if (pozitieEditata != -1) {
                        laptopList.set(pozitieEditata, laptopPrimit);
                        pozitieEditata = -1; // Resetăm variabila
                        Toast.makeText(this, "Laptop modificat!", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }
    }
}