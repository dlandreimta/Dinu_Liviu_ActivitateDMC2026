package com.example.laborator11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private HashMap<String, Integer> chartData = new HashMap<>();
    private EditText etLabel, etValue;
    private TextView tvDataPreview;
    private Spinner spinnerChartType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLabel = findViewById(R.id.etLabel);
        etValue = findViewById(R.id.etValue);
        tvDataPreview = findViewById(R.id.tvDataPreview);
        spinnerChartType = findViewById(R.id.spinnerChartType);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnShowChart = findViewById(R.id.btnShowChart);

        btnAdd.setOnClickListener(v -> {
            String label = etLabel.getText().toString().trim();
            String valueStr = etValue.getText().toString().trim();

            if (!label.isEmpty() && !valueStr.isEmpty()) {
                try {
                    int value = Integer.parseInt(valueStr);
                    chartData.put(label, value);
                    updatePreview();
                    etLabel.setText("");
                    etValue.setText("");
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Valoare invalidă", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Completați ambele câmpuri", Toast.LENGTH_SHORT).show();
            }
        });

        btnShowChart.setOnClickListener(v -> {
            if (chartData.isEmpty()) {
                Toast.makeText(this, "Adăugați date mai întâi", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            intent.putExtra("chart_data", chartData);
            intent.putExtra("chart_type", spinnerChartType.getSelectedItem().toString());
            startActivity(intent);
        });
    }

    private void updatePreview() {
        StringBuilder sb = new StringBuilder("Date adăugate:\n");
        for (String key : chartData.keySet()) {
            sb.append(key).append(": ").append(chartData.get(key)).append("\n");
        }
        tvDataPreview.setText(sb.toString());
    }
}
