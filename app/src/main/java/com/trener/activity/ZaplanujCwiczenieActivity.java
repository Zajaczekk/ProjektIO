package com.trener.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class ZaplanujCwiczenieActivity extends AppCompatActivity {

    Spinner treningi, cwiczenia;
    final List<String> zbiorCwiczen = new ArrayList<String>();
    final List<String> zbiorCwiczenn = new ArrayList<String>();
    public ArrayList<Map<String,String>> listaCwiczen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaplanuj_cwiczenie);

        treningi = (Spinner) findViewById(R.id.spTreningi);
        cwiczenia = (Spinner) findViewById(R.id.spCwiczeniaT);
        listaCwiczen = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/treningi.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                String nazwa = c.getString("nazwa_treningu");
                                String id_t = c.getString("id_treningu_pk");
                                zbiorCwiczen.add(nazwa);

                                HashMap<String,String> cwiczenie = new HashMap<>();
                                cwiczenie.put("nazwa_treningu",nazwa);
                                cwiczenie.put("id_treningu_pk",id_t);

                                listaCwiczen.add(cwiczenie);
                            }

                            String[] arr = new String[zbiorCwiczen.size()];
                            for (int i=0; i<zbiorCwiczen.size();i++){
                                arr[i] = zbiorCwiczen.get(i);
                            }

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, zbiorCwiczen);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            treningi.setAdapter(adapter);

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

        RequestQueue queuee = Volley.newRequestQueue(this);

        int temp = treningi.getSelectedItemPosition();
        System.out.println(String.valueOf(temp));
        String urll ="http://149.202.92.150/projekt/rest/treningi.php?id=" + String.valueOf(temp);

        StringRequest stringRequestt = new StringRequest(Request.Method.GET, urll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                String nazwa = c.getString("nazwa_treningu");

                                zbiorCwiczenn.add(nazwa);
                            }

                            String[] arr = new String[zbiorCwiczenn.size()];
                            for (int i=0; i<zbiorCwiczenn.size();i++){
                                arr[i] = zbiorCwiczenn.get(i);
                            }

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, zbiorCwiczenn);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            treningi.setAdapter(adapter);

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

        queuee.add(stringRequestt);
    }
}
