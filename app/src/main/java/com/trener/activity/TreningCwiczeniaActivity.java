package com.trener.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import info.androidhive.loginandregistration.R;

public class TreningCwiczeniaActivity extends AppCompatActivity {

    TextView tvCwiczenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_cwiczenia);

        long id_treningu = getIntent().getLongExtra("value",1);
        tvCwiczenia = (TextView) findViewById(R.id.tvTreningCwiczenia);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/freeszymek.php?id=" + String.valueOf(id_treningu);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            if (cwiczenia.length() == 0){
                                tvCwiczenia.append("Brak ćwiczeń w tym treningu.");
                            }
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                tvCwiczenia.append("Nazwa: " + c.get("nazwa_c") + "\n" + "Ilość serii: " + c.getString("ilosc_serii") + "\n" + "Ilość powtórzeń: " + c.getString("ilosc_pow") + "\n" + "Ciężar: " + c.getString("ciezar") + "\n" + "Dystans: " + c.getString("dystans") + "\n\n\n");

                            }

                            tvCwiczenia.setMovementMethod(new ScrollingMovementMethod());

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
