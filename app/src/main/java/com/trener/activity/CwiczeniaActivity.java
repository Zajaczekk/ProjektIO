package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trener.helper.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class CwiczeniaActivity extends AppCompatActivity {

    ArrayList<Map<String,String>> listaCwiczen;
    SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwiczenia);

        Button btnDodajCwiczenie = (Button) findViewById(R.id.btnDodajCwiczenie);
        final ListView lvCwiczenia = (ListView) findViewById(R.id.lvCwiczenia) ;
        listaCwiczen = new ArrayList<>();
        final List<String> zbiorCwiczen = new ArrayList<String>();
        db = new SQLiteHandler(getApplicationContext());

        btnDodajCwiczenie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        DodajCwiczenieActivity.class);
                startActivity(i);
                finish();
            }

        });

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/cwiczenia.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                String nazwa_c = c.getString("nazwa_c");
                                String informacja = c.getString("informacja");
                                String ilosc_serii = c.getString("ilosc_serii");
                                String ilosc_pow = c.getString("ilosc_pow");
                                String dystans = c.getString("dystans");
                                String ciezar = c.getString("ciezar");

                                HashMap<String,String> cwiczenie = new HashMap<>();

                                zbiorCwiczen.add(nazwa_c);
                                cwiczenie.put("nazwa_c",nazwa_c);
                                cwiczenie.put("informacja",informacja);
                                cwiczenie.put("ilosc_serii",ilosc_serii);
                                cwiczenie.put("ilosc_pow",ilosc_pow);
                                cwiczenie.put("dystans",dystans);
                                cwiczenie.put("ciezar",ciezar);

                                listaCwiczen.add(cwiczenie);
                                db.dodajCwiczenie(nazwa_c,informacja);
                            }

                            String[] arr = new String[zbiorCwiczen.size()];
                            for (int i=0; i<zbiorCwiczen.size();i++){
                                arr[i] = zbiorCwiczen.get(i);
                            }

                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaCwiczen,R.layout.list_item, new String[]{"nazwa_c","informacja"},new int[]{R.id.nazwaC,R.id.informacjaC});
                            lvCwiczenia.setAdapter(adapter);

                            //ArrayAdapter<String> cwiczeniaAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.cwiczenie_name, arr);
                            //lvCwiczenia.setAdapter(cwiczeniaAdapter);


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

        //ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaCwiczen,R.layout.list_item, new String[]{"nazwa_c","informacja"},new int[]{R.id.nazwaC,R.id.informacjaC});
        //lvCwiczenia.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();
    }
}
