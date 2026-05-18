package com.example.laborator11;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        BarChartView barChartView = findViewById(R.id.barChart);
        ColumnChartView columnChartView = findViewById(R.id.columnChart);
        PieChartView pieChartView = findViewById(R.id.pieChart);

        if (getIntent() != null && getIntent().hasExtra("chart_data")) {
            HashMap<String, Integer> data = (HashMap<String, Integer>) getIntent().getSerializableExtra("chart_data");
            String chartType = getIntent().getStringExtra("chart_type");

            if (data != null && chartType != null) {
                barChartView.setVisibility(View.GONE);
                columnChartView.setVisibility(View.GONE);
                pieChartView.setVisibility(View.GONE);

                if (chartType.equals("Pie Chart")) {
                    pieChartView.setVisibility(View.VISIBLE);
                    pieChartView.setData(data);
                } else if (chartType.equals("Column Chart")) {
                    columnChartView.setVisibility(View.VISIBLE);
                    columnChartView.setData(data);
                } else if (chartType.equals("Bar Chart")) {
                    barChartView.setVisibility(View.VISIBLE);
                    barChartView.setData(data);
                }
            }
        }
    }
}
