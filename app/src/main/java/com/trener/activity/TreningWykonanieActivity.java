package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class TreningWykonanieActivity extends AppCompatActivity {

    Button btnZapisz;
    EditText et1, et2, et3, et4;
    TextView tv1, tv2, tv3, tv4, tv5;
    String war_cwic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_wykonanie);

        final String id_tren = getIntent().getStringExtra("id_treningu");
        System.out.println("I: " + id_tren);
        long numerT = getIntent().getLongExtra("numerT",1);
        final long numerCW = getIntent().getLongExtra("numerCW",1);

        btnZapisz = (Button) findViewById(R.id.btnZapisz);
        et1 = (EditText) findViewById(R.id.editText2);
        et2 = (EditText) findViewById(R.id.editText5);
        et3 = (EditText) findViewById(R.id.editText6);
        et4 = (EditText) findViewById(R.id.editText7);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tvSerie);
        tv3 = (TextView) findViewById(R.id.textView10);
        tv4 = (TextView) findViewById(R.id.textView11);
        tv5 = (TextView) findViewById(R.id.textView12);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/freeszymek.php?id=" + String.valueOf(numerT);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            JSONObject c = cwiczenia.getJSONObject((int) numerCW);

                            String nazwa = c.getString("nazwa_c");
                            String seria = c.getString("ilosc_serii");
                            String pow = c.getString("ilosc_pow");
                            String dyst = c.getString("dystans");
                            String ciez = c.getString("ciezar");
                            war_cwic = c.getString("id_war_cwic_pk");

                            tv1.append(nazwa);
                            tv2.append(seria);
                            tv3.append(pow);
                            tv4.append(dyst);
                            tv5.append(ciez);


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




        btnZapisz.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://149.202.92.150/projekt/rest/dodaniewykcwiczenia.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            System.out.println("R: " + response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Błąd.", Toast.LENGTH_SHORT).show();
                        System.out.println("E: " + error);
                    }

                }){
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id_uzytkownika_fk","1");
                        params.put("ilosc_serii",et1.getText().toString());
                        params.put("ilosc_pow",et2.getText().toString());
                        params.put("dystans",et3.getText().toString());
                        params.put("ciezar",et4.getText().toString());
                        params.put("id_wyk_tren_fk",id_tren);
                        params.put("id_war_cwic_fk",war_cwic);

                        return params;
                    }};

                queue.add(stringRequest);
                finish();
            }
        });


    }
}
