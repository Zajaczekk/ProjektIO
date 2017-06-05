package com.trener.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.loginandregistration.R;

public class TreningRaportActivity extends AppCompatActivity {

    String id_tren;
    long id_cw;
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_raport);

        id_tren = getIntent().getStringExtra("id_tren");
        id_cw = getIntent().getLongExtra("id",1);
        chart = (BarChart) findViewById(R.id.chart);
        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("    Serie");
        labels.add("");
        labels.add("    Powtórzenia");
        labels.add("");
        labels.add("Ciężar");
        labels.add("");
        labels.add("Dystans");


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/selecttrzytrzy.php?id=" + String.valueOf(id_tren);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            JSONObject c = cwiczenia.getJSONObject((int)id_cw);
                            System.out.println(c);

                            String nazwa = c.getString("nazwa_c");
                            String wciloscserii = c.getString("wciloscserii");
                            String pciloscserii = c.getString("pciloscserii");
                            String wcilosc_pow = c.getString("wcilosc_pow");
                            String pcilosc_pow = c.getString("pcilosc_pow");
                            String wcciezar = c.getString("wcciezar");
                            String pcciezar = c.getString("pcciezar");
                            String wcdystans = c.getString("wcdystans");
                            String pcdystans = c.getString("pcdystans");

                            ArrayList<BarEntry> entries = new ArrayList<>();
                            entries.add(new BarEntry(0,Float.valueOf(pciloscserii)));
                            entries.add(new BarEntry(2,Float.valueOf(pcilosc_pow)));
                            entries.add(new BarEntry(4,Float.valueOf(pcciezar)));
                            entries.add(new BarEntry(6,Float.valueOf(pcdystans)));

                            ArrayList<BarEntry> entries2 = new ArrayList<>();
                            entries2.add(new BarEntry(1,Float.valueOf(wciloscserii)));
                            entries2.add(new BarEntry(3,Float.valueOf(wcilosc_pow)));
                            entries2.add(new BarEntry(5,Float.valueOf(wcciezar)));
                            entries2.add(new BarEntry(7,Float.valueOf(wcdystans)));


                            BarDataSet dataset = new BarDataSet(entries, "Planowane");
                            dataset.setColor(Color.RED);
                            BarDataSet dataset2 = new BarDataSet(entries2, "Wykonane");
                            dataset2.setColor(Color.BLUE);

                            BarData data = new BarData(dataset2, dataset);
                            chart.setData(data);
                            chart.invalidate();

                            XAxis xAxis = chart.getXAxis();
                            YAxis left = chart.getAxisLeft();
                            chart.getAxisRight().setGranularity(1);
                            chart.getAxisRight().setStartAtZero(true);
                            chart.getXAxis().setGranularity(1);
                            chart.getXAxis().setAxisMaximum(8);
                            chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                            chart.setDrawGridBackground(false);
                            xAxis.setDrawGridLines(false);
                            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

                            left.setGranularity(1);
                            left.setStartAtZero(true);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Błąd.", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }
}
