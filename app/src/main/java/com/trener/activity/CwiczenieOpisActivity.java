package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trener.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class CwiczenieOpisActivity extends AppCompatActivity {

    TextView nazwaCw;
    TextView txtV;
    Button btnEdytuj;
    Button btnUsun;
    HashMap<String,String> cwiczenie = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwiczenie_opis);
        final long value = getIntent().getLongExtra("value",1);
        nazwaCw = (TextView)findViewById(R.id.tVNazwaCw);
        txtV = (TextView)findViewById(R.id.textView3);
        btnEdytuj = (Button) findViewById(R.id.btnEdytuj);
        btnUsun = (Button) findViewById(R.id.button2);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/cwiczenia.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray a = new JSONArray(response);
                            JSONObject c = a.getJSONObject((int) value);

                            String nazwa_c = c.getString("nazwa_c");
                            String informacja = c.getString("informacja");
                            String ilosc_serii = c.getString("ilosc_serii");
                            String ilosc_pow = c.getString("ilosc_pow");
                            String dystans = c.getString("dystans");
                            String ciezar = c.getString("ciezar");

                            cwiczenie.put("nazwa_c",nazwa_c);
                            cwiczenie.put("informacja",informacja);
                            cwiczenie.put("ilosc_serii",ilosc_serii);
                            cwiczenie.put("ilosc_pow",ilosc_pow);
                            cwiczenie.put("dystans",dystans);
                            cwiczenie.put("ciezar",ciezar);

                            nazwaCw.append(cwiczenie.get("nazwa_c"));
                            txtV.setText(informacja);

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

        btnEdytuj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        EdytujActivity.class);
                i.putExtra("numer",value);
                startActivity(i);
                finish();
            }
        });

        btnUsun.setOnClickListener(new View.OnClickListener() {
            String urlUsun = "";

            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUsun,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Błąd.", Toast.LENGTH_SHORT).show();
                    }

                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("czyUsunac", "1");

                        return params;
                    }};

                queue.add(stringRequest);

                Intent i = new Intent(getApplicationContext(),
                        CwiczeniaActivity.class);
                startActivity(i);
                finish();
            }

        });


        //opisCw.append("a");
    }
}
