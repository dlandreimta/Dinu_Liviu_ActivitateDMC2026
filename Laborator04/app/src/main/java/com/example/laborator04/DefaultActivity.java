package com.example.laborator04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DefaultActivity extends AppCompatActivity {

    private List<DispozitivLaptop> laptopList = new ArrayList<>();
    private ArrayAdapter<DispozitivLaptop> adapter;
    private ListView lvLaptops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        lvLaptops = findViewById(R.id.lvLaptops);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, laptopList);
        lvLaptops.setAdapter(adapter);

        lvLaptops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DispozitivLaptop laptopSelectat = laptopList.get(position);
                Toast.makeText(DefaultActivity.this,
                        "Detalii: " + laptopSelectat.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        lvLaptops.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                laptopList.remove(position);
                adapter.notifyDataSetChanged();
                Toast.makeText(DefaultActivity.this, "Laptop eliminat!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Button btnAdd = findViewById(R.id.btnOpenAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DefaultActivity.this, AddLaptopActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                DispozitivLaptop laptopPrimit = (DispozitivLaptop) bundle.getSerializable("laptop_obiect");

                if (laptopPrimit != null) {
                    laptopList.add(laptopPrimit);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}