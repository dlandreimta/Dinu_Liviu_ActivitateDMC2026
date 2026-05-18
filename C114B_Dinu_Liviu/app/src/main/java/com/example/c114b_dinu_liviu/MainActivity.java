package com.example.c114b_dinu_liviu;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private android.widget.ListView lvEvenimente;
    private java.util.List<Eveniment> evenimentList = new ArrayList<>();
    private EvenimentAdapter adapter;

    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> addEvenimentLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvEvenimente = findViewById(R.id.dllvEvenimente);
        adapter = new EvenimentAdapter(this, R.layout.item_eveniment, evenimentList);
        lvEvenimente.setAdapter(adapter);

        lvEvenimente.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                Eveniment eveniment = evenimentList.get(position);
                android.widget.Toast.makeText(MainActivity.this, eveniment.toString(), android.widget.Toast.LENGTH_LONG).show();
            }
        });

        loadFromFile();

        addEvenimentLauncher = registerForActivityResult(
                new androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
                (androidx.activity.result.ActivityResult result) -> {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Eveniment eveniment = result.getData().getParcelableExtra("eveniment_key");
                        if (eveniment != null) {
                            evenimentList.add(eveniment);
                            adapter.notifyDataSetChanged();

                            saveOnFile();
                        }
                    }
                }
        );


        FloatingActionButton fabAdd = findViewById(R.id.dlfabAdd);
        fabAdd.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                addEvenimentLauncher.launch(new android.content.Intent(MainActivity.this, AddEvenimentActivity.class));
            }
        });

        android.widget.Button btnActivity3 = findViewById(R.id.dlbtnOpenActivity3);
        btnActivity3.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(MainActivity.this, Activity3.class);
                intent.putParcelableArrayListExtra("evenimente_list", (java.util.ArrayList<Eveniment>) evenimentList);
                startActivity(intent);
            }
        });

    }

    private void saveOnFile() {
        try {
            java.io.FileOutputStream fos = openFileOutput("evenimente.txt", android.content.Context.MODE_PRIVATE);
            java.io.PrintWriter writer = new java.io.PrintWriter(fos);

            for (Eveniment eveniment : evenimentList) {
                writer.println(
                        eveniment.getDenumire() + "|" +
                        eveniment.getLocatie() + "|" +
                        eveniment.getData() + "|" +
                        eveniment.getDescriere() + "|" +
                        eveniment.isSensibil()

                );
            }
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try {
            java.io.FileInputStream fis = openFileInput("evenimente.txt");
            java.util.Scanner scanner = new java.util.Scanner(fis);

            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                if (linie.trim().isEmpty()) continue;

                String[] bucati = linie.split("\\|");

                if (bucati.length == 5) {
                    try {
                        Eveniment e = new Eveniment(
                                bucati[0],
                                bucati[1],
                                bucati[2],
                                bucati[3],
                                Boolean.parseBoolean(bucati[4])
                        );
                        evenimentList.add(e);
                    } catch (Exception e) {
                        android.util.Log.e("EROARE_EVENIMENT", "Linie coruptă: " + linie);
                    }
                }
            }
            scanner.close();

            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        } catch (java.io.FileNotFoundException e) {
        }
    }
}