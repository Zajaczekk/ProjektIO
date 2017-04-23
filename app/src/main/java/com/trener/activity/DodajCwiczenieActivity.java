package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.trener.app.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

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
        etNazwaCwiczenia = (EditText)findViewById(R.id.etNazwaCwiczenia);
        etOpis = (EditText)findViewById(R.id.etOpis);
        spPartia = (Spinner)findViewById(R.id.spPartia);
        ciezar = (CheckBox) findViewById(R.id.chbCiezar);
        czas = (CheckBox) findViewById(R.id.chbCzas);
        dystans = (CheckBox)findViewById(R.id.chbDystans);
        powt = (CheckBox) findViewById(R.id.chbPowtorzenia);
        serie = (CheckBox) findViewById(R.id.chbSerie);


        btnDodaj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Method.POST, AppConfig.URL_ADD_EXERCISE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {



                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                JSONObject data = new JSONObject();
                try{
                    data.put("nazwa_c",etNazwaCwiczenia.getText().toString());
                    data.put("nazwa_p_fk",spPartia.getSelectedItem().toString());
                    data.put("informacja",etOpis.getText().toString());

                    if (serie.isChecked()) {
                        data.put("ilosc_serii", "1");
                    } else {
                        data.put("ilosc_serii", "0");
                    }

                    if (powt.isChecked()) {
                        data.put("ilosc_pow", "1");
                    } else {
                        data.put("ilosc_pow", "0");
                    }

                    if (dystans.isChecked()) {
                        data.put("dystans", "1");
                    } else {
                        data.put("dystans", "0");
                    }

                    if (ciezar.isChecked()) {
                        data.put("ciezar", "1");
                    } else {
                        data.put("ciezar", "0");
                    }

                    if (czas.isChecked()) {
                        data.put("czas", "1");
                    } else {
                        data.put("czas", "0");
                    }

                    data.put("id_uzytkownika_fk","1");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
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
