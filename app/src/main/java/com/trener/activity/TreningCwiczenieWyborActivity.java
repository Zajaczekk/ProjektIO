package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class TreningCwiczenieWyborActivity extends AppCompatActivity {

    ListView cwiczeniaLV;
    Button btnZakonczTrening;
    public ArrayList<Map<String,String>> listaCwiczen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_cwiczenie_wybor);

        cwiczeniaLV = (ListView)findViewById(R.id.lvCw1);
        final long numerT = getIntent().getLongExtra("numerT",1);
        final String id_treningu = getIntent().getStringExtra("id_treningu");
        btnZakonczTrening = (Button) findViewById(R.id.btnZakonczTrening);
        listaCwiczen = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/freeszymek.php?id=" + String.valueOf(numerT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                String nazwa = c.getString("nazwa_c");

                                HashMap<String,String> cwiczenie = new HashMap<>();
                                cwiczenie.put("nazwa_c",nazwa);

                                listaCwiczen.add(cwiczenie);
                            }

                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaCwiczen,R.layout.item, new String[]{"nazwa_c"},new int[]{R.id.nazwaCw});
                            cwiczeniaLV.setAdapter(adapter);


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

        cwiczeniaLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),TreningWykonanieActivity.class);
                intent.putExtra("id_treningu",id_treningu);
                intent.putExtra("numerT",numerT);
                long numerCW = id;
                intent.putExtra("numerCW",numerCW);
                startActivity(intent);
            }
        });

        btnZakonczTrening.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        TreningiActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
