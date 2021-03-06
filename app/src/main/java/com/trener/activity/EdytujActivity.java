package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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

public class EdytujActivity extends AppCompatActivity {

    Button btnEdit;
    EditText nazwaCw;
    EditText opisCw;
    CheckBox serie, powt, czas, dystans, ciezar;
    String nazwa, opis, seria, pow, dyst, ciez, part, id;
    Spinner partia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        nazwaCw = (EditText) findViewById(R.id.etNazwaCwiczeniaE);
        opisCw = (EditText) findViewById(R.id.etOpisE);
        serie = (CheckBox) findViewById(R.id.chbSerieE);
        powt = (CheckBox) findViewById(R.id.chbPowtorzeniaE);
        dystans = (CheckBox) findViewById(R.id.chbDystansE);
        ciezar = (CheckBox) findViewById(R.id.chbCiezarE);
        partia = (Spinner) findViewById(R.id.spPartiaE);

        final long nr = getIntent().getLongExtra("numer",0);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/cwiczenia.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray a = new JSONArray(response);
                            JSONObject c = a.getJSONObject((int)nr);
                            System.out.println(response);

                            part = c.getString("nazwa_p_fk");

                            id = c.getString("id_cwiczenia_pk");
                            nazwa = c.getString("nazwa_c");
                            opis = c.getString("informacja");
                            seria = c.getString("ilosc_serii");
                            pow = c.getString("ilosc_pow");
                            dyst = c.getString("dystans");
                            ciez = c.getString("ciezar");

                            nazwaCw.setText(nazwa.toString());
                            opisCw.setText(opis.toString());

                            if (seria.equals("1")){
                                serie.setChecked(true);
                            }
                            if (pow.equals("1")){
                                powt.setChecked(true);
                            }
                            if (dyst.equals("1")){
                                dystans.setChecked(true);
                            }
                            if (ciez.equals("1")){
                                ciezar.setChecked(true);
                            }


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

        btnEdit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://149.202.92.150/projekt/rest/modifikowaniecwiczenia.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
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
                        params.put("nazwa_c",nazwaCw.getText().toString());
                        params.put("informacja",opisCw.getText().toString());
                        if (serie.isChecked()){
                            params.put("ilosc_serii","1");
                        }
                        else{
                            params.put("ilosc_serii","0");
                        }
                        if (ciezar.isChecked()){
                            params.put("ciezar","1");
                        }
                        else{
                            params.put("ciezar","0");
                        }
                        if (dystans.isChecked()){
                            params.put("dystans","1");
                        }
                        else{
                            params.put("dystans","1");
                        }
                        if (powt.isChecked()){
                            params.put("ilosc_pow","1");
                        }
                        else{
                            params.put("ilosc_pow","0");
                        }

                        params.put("nazwa_p_fk",partia.getSelectedItem().toString());
                        params.put("id_cwiczenia_pk",id);


                        return params;
                    }};

                queue.add(stringRequest);

                Intent i = new Intent(getApplicationContext(),
                        CwiczeniaActivity.class);
                startActivity(i);
                finish();
            }

        });
    }



    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                CwiczeniaActivity.class);
        startActivity(i);
        finish();
    }
}
