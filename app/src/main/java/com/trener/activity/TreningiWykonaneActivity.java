package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class TreningiWykonaneActivity extends AppCompatActivity {

    public ArrayList<Map<String,String>> listaTreningow;
    ListView lvTreningi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treningi_wykonane);

        listaTreningow = new ArrayList<>();
        lvTreningi = (ListView) findViewById(R.id.lvWykonaneTreningi);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/selecttrzydwa.php?id=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray treningi = new JSONArray(response);
                            for (int i=0; i<treningi.length(); i++){
                                JSONObject t = treningi.getJSONObject(i);

                                String nazwa = t.getString("nazwa_treningu");
                                String id_wyk_tren_pk = t.getString("id_wyk_tren_pk");
                                String data = t.getString("data_wykonania");
                                String czas = t.getString("godzina_wykonania");

                                HashMap<String,String> trening = new HashMap<>();

                                trening.put("nazwa",nazwa);
                                trening.put("id",id_wyk_tren_pk);
                                trening.put("dataCzas",data + " | " + czas);

                                listaTreningow.add(trening);
                            }


                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaTreningow,R.layout.list_item, new String[]{"nazwa","dataCzas"},new int[]{R.id.nazwaC,R.id.informacjaC});
                            lvTreningi.setAdapter(adapter);


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

        lvTreningi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),TreningiWykonaneCwiczeniaActivity.class);
                intent.putExtra("id_treningu",listaTreningow.get((int)id).get("id"));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),
                TreningiActivity.class);
        startActivity(i);
        finish();
    }
}
