package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.trener.app.AppConfig;

import java.util.HashMap;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class DodajCwiczenieActivity extends AppCompatActivity {

    Button btnDodaj;
    EditText etNazwaCwiczenia, etOpis;
    Spinner spPartia;
    CheckBox ciezar, czas, dystans, powt, serie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_cwiczenie);
        btnDodaj = (Button)findViewById(R.id.btnDodaj);
        etNazwaCwiczenia = (EditText)findViewById(R.id.etNazwaCwiczeniaE);
        etOpis = (EditText)findViewById(R.id.etOpisE);
        spPartia = (Spinner)findViewById(R.id.spPartiaE);
        ciezar = (CheckBox) findViewById(R.id.chbCiezarE);
        czas = (CheckBox) findViewById(R.id.chbCzasE);
        dystans = (CheckBox)findViewById(R.id.chbDystansE);
        powt = (CheckBox) findViewById(R.id.chbPowtorzeniaE);
        serie = (CheckBox) findViewById(R.id.chbSerieE);


        btnDodaj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Method.POST, AppConfig.URL_ADD_EXERCISE,
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
                    params.put("nazwa_c", etNazwaCwiczenia.getText().toString());
                    params.put("nazwa_p_fk", spPartia.getSelectedItem().toString());
                    params.put("informacja", etOpis.getText().toString());
                    params.put("id_cwiczenia_pk",String.valueOf(AppConfig.id_c));
                    AppConfig.id_c++;

                    if (serie.isChecked()) {
                        params.put("ilosc_serii", "1");
                    } else {
                        params.put("ilosc_serii", "0");
                    }

                    if (powt.isChecked()) {
                        params.put("ilosc_pow", "1");
                    } else {
                        params.put("ilosc_pow", "0");
                    }

                    if (dystans.isChecked()) {
                        params.put("dystans", "1");
                    } else {
                        params.put("dystans", "0");
                    }

                    if (ciezar.isChecked()) {
                        params.put("ciezar", "1");
                    } else {
                        params.put("ciezar", "0");
                    }

                    if (czas.isChecked()) {
                        params.put("czas", "1");
                    } else {
                        params.put("czas", "0");
                    }

                    params.put("id_uzytkownika_fk", "1");

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
