package com.example.laborator10;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etCityName, etChartValues;
    private Spinner spinnerDays;
    private Button btnSearch, btnPieChart, btnBarChart, btnColumnChart;
    private TextView tvResult;
    private PieChart pieChart;
    private BarChart barChart;

    // API KEY furnizat
    private static final String API_KEY = "REMOVED_FOR_SECURITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Weather views
        etCityName = findViewById(R.id.etCityName);
        spinnerDays = findViewById(R.id.spinnerDays);
        btnSearch = findViewById(R.id.btnSearch);
        tvResult = findViewById(R.id.tvResult);

        // Chart views
        etChartValues = findViewById(R.id.etChartValues);
        btnPieChart = findViewById(R.id.btnPieChart);
        btnBarChart = findViewById(R.id.btnBarChart);
        btnColumnChart = findViewById(R.id.btnColumnChart);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = etCityName.getText().toString().trim();
                String selectedOption = spinnerDays.getSelectedItem().toString();
                
                int days = 1;
                if (selectedOption.contains("5")) {
                    days = 5;
                } else if (selectedOption.contains("10")) {
                    days = 10;
                }

                if (!cityName.isEmpty()) {
                    new WeatherTask(cityName, days).execute();
                } else {
                    tvResult.setText("Vă rugăm să introduceți numele unui oraș.");
                }
            }
        });

        btnPieChart.setOnClickListener(v -> showPieChart());
        btnBarChart.setOnClickListener(v -> showBarChart());
        btnColumnChart.setOnClickListener(v -> showBarChart()); // In MPAndroidChart, BarChart is Column by default
    }

    private List<Float> getChartInputs() {
        String input = etChartValues.getText().toString().trim();
        List<Float> values = new ArrayList<>();
        if (input.isEmpty()) {
            Toast.makeText(this, "Introduceți valori separate prin virgulă", Toast.LENGTH_SHORT).show();
            return values;
        }
        try {
            String[] parts = input.split(",");
            for (String part : parts) {
                values.add(Float.parseFloat(part.trim()));
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Format invalid! Folosiți cifre și virgulă.", Toast.LENGTH_SHORT).show();
        }
        return values;
    }

    private void showPieChart() {
        List<Float> values = getChartInputs();
        if (values.isEmpty()) return;

        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);

        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entries.add(new PieEntry(values.get(i), "Valoare " + (i + 1)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Date Manuale");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);
        pieChart.invalidate();
    }

    private void showBarChart() {
        List<Float> values = getChartInputs();
        if (values.isEmpty()) return;

        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            entries.add(new BarEntry(i, values.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Date Manuale");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        
        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    // Task pentru căutarea prognozei
    private class WeatherTask extends AsyncTask<Void, String, String> {
        private String cityName;
        private int days;

        public WeatherTask(String cityName, int days) {
            this.cityName = cityName;
            this.days = days;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String encodedCity = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
                String citySearchUrl = "https://dataservice.accuweather.com/locations/v1/cities/search?q=" + encodedCity;
                
                publishProgress("Se caută orașul: " + cityName + "...");
                String cityResponse = makeRequest(citySearchUrl);
                JSONArray cityArray = new JSONArray(cityResponse);

                if (cityArray.length() == 0) {
                    return "Orașul '" + cityName + "' nu a fost găsit.";
                }

                JSONObject cityObj = cityArray.getJSONObject(0);
                String cityKey = cityObj.getString("Key");
                String localizedName = cityObj.getString("LocalizedName");

                publishProgress("Oraș găsit: " + localizedName + "\nCod (Key): " + cityKey + "\n\nSe încarcă prognoza...");

                // Pasul 2: Obținem prognoza meteo
                String forecastUrl = "https://dataservice.accuweather.com/forecasts/v1/daily/" + days + "day/" + cityKey + "?metric=true";
                String forecastResponse = makeRequest(forecastUrl);
                JSONObject forecastObj = new JSONObject(forecastResponse);
                JSONArray dailyForecasts = forecastObj.getJSONArray("DailyForecasts");

                StringBuilder sb = new StringBuilder();
                sb.append("Oraș: ").append(localizedName).append(" (").append(cityKey).append(")\n\n");

                for (int i = 0; i < dailyForecasts.length(); i++) {
                    JSONObject forecast = dailyForecasts.getJSONObject(i);
                    String date = forecast.getString("Date").substring(0, 10);
                    JSONObject temp = forecast.getJSONObject("Temperature");
                    double min = temp.getJSONObject("Minimum").getDouble("Value");
                    double max = temp.getJSONObject("Maximum").getDouble("Value");
                    String unit = temp.getJSONObject("Minimum").getString("Unit");

                    sb.append("Data: ").append(date).append("\n");
                    sb.append("Temp: ").append(min).append("°").append(unit).append(" - ").append(max).append("°").append(unit).append("\n\n");
                }

                return sb.toString();

            } catch (Exception e) {
                Log.e("WeatherApp", "Error in WeatherTask", e);
                return "Eroare: " + e.getMessage();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            tvResult.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            tvResult.setText(result);
        }
    }


    private String makeRequest(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(10000);
        conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
        conn.setRequestProperty("Accept", "application/json");
        
        int responseCode = conn.getResponseCode();
        InputStream is;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        if (is == null) {
            throw new Exception("HTTP Error: " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        reader.close();

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new Exception("HTTP " + responseCode + ": " + result.toString());
        }

        return result.toString();
    }
}
