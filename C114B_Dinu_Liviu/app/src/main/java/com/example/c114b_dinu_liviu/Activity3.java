package com.example.c114b_dinu_liviu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Activity3 extends AppCompatActivity {

    private List<Eveniment> evenimentList;
    private ListView lvFiltered;
    private EvenimentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        evenimentList = getIntent().getParcelableArrayListExtra("evenimente_list");
        if (evenimentList == null) {
            evenimentList = new ArrayList<>();
        }

        lvFiltered = findViewById(R.id.dllvFiltered);
        
        Button btnFilterBucuresti = findViewById(R.id.dlbtnFilterBucuresti);
        btnFilterBucuresti.setOnClickListener(v -> {
            List<Eveniment> filteredList = new ArrayList<>();
            for (Eveniment e : evenimentList) {
                if (e.getLocatie() != null && e.getLocatie().equalsIgnoreCase("Bucuresti")) {
                    filteredList.add(e);
                }
            }
            adapter = new EvenimentAdapter(Activity3.this, R.layout.item_eveniment, filteredList);
            lvFiltered.setAdapter(adapter);
        });

        findViewById(R.id.dlbtnTwo).setOnClickListener(v -> {
            int initialSize = evenimentList.size();
            List<Eveniment> toRemove = new ArrayList<>();

            for (Eveniment e : evenimentList) {
                if (e.getData() != null && e.getData().endsWith("/2025")) {
                    toRemove.add(e);
                }
            }

            evenimentList.removeAll(toRemove);
            int deletedCount = initialSize - evenimentList.size();

            adapter = new EvenimentAdapter(Activity3.this, R.layout.item_eveniment, evenimentList);
            lvFiltered.setAdapter(adapter);

            android.widget.Toast.makeText(Activity3.this, "Numar intrari sterse: " + deletedCount, android.widget.Toast.LENGTH_LONG).show();
        });

        findViewById(R.id.dlbtnThree).setOnClickListener(v -> {
            if (evenimentList != null && !evenimentList.isEmpty()) {
                Eveniment ev = evenimentList.get(0);
                String dataInitiala = ev.getData();
                String dataActualizata = "20/10/2025";
                ev.setData(dataActualizata);

                adapter = new EvenimentAdapter(Activity3.this, R.layout.item_eveniment, evenimentList);
                lvFiltered.setAdapter(adapter);

                android.widget.Toast.makeText(Activity3.this,
                        "Initiala: " + dataInitiala + ", Actualizata: " + dataActualizata,
                        android.widget.Toast.LENGTH_LONG).show();
            }
        });
    }
}
