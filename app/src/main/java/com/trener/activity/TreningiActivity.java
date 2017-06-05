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
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class TreningiActivity extends AppCompatActivity {

    ListView lvTrening;
    Button btnWykonajTrening, btnWykonaneTreningi;
    public ArrayList<Map<String,String>> listaTreningow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treningi);

        lvTrening = (ListView)findViewById(R.id.lvTrening);
        btnWykonajTrening = (Button) findViewById(R.id.btnWykonajTrening);
        btnWykonaneTreningi = (Button) findViewById(R.id.btnWykonaneTreningi);
        listaTreningow = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/treningi.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response);

                        try {
                            JSONArray treningi = new JSONArray(response);
                            for (int i=0; i<treningi.length(); i++){
                                JSONObject trening = treningi.getJSONObject(i);

                                HashMap<String,String> t = new HashMap<>();
                                t.put("nazwa_treningu",trening.getString("nazwa_treningu"));

                                listaTreningow.add(t);

                                ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaTreningow,R.layout.item, new String[]{"nazwa_treningu"},new int[]{R.id.nazwaCw});

                                lvTrening.setAdapter(adapter);
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

        lvTrening.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),TreningCwiczeniaActivity.class);
                long v = id+1;
                intent.putExtra("value",v);
                startActivity(intent);
            }
        });

        btnWykonajTrening.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        TreningWyborActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnWykonaneTreningi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        TreningiWykonaneActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(i);
        finish();
    }
}
