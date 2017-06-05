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
import com.trener.app.AppConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class TreningWyborActivity extends AppCompatActivity {

    ListView treningi;
    String id_tren;
    final List<String> zbiorCwiczen = new ArrayList<String>();
    public ArrayList<Map<String,String>> listaCwiczen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening_wybor);

        treningi = (ListView) findViewById(R.id.lvTreningi2);
        listaCwiczen = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/treningi.php?id=2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            for (int i=0; i<cwiczenia.length(); i++){
                                JSONObject c = cwiczenia.getJSONObject(i);

                                String nazwa = c.getString("nazwa_treningu");
                                String id_t = c.getString("id_treningu_pk");
                                zbiorCwiczen.add(nazwa);

                                HashMap<String,String> cwiczenie = new HashMap<>();
                                cwiczenie.put("nazwa_treningu",nazwa);
                                cwiczenie.put("id_treningu_pk",id_t);

                                listaCwiczen.add(cwiczenie);
                            }

                            String[] arr = new String[zbiorCwiczen.size()];
                            for (int i=0; i<zbiorCwiczen.size();i++){
                                arr[i] = zbiorCwiczen.get(i);
                            }

                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listaCwiczen,R.layout.item, new String[]{"nazwa_treningu"},new int[]{R.id.nazwaCw});

                            treningi.setAdapter(adapter);

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

        treningi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                int hur = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);
                int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                int month = Calendar.getInstance().get(Calendar.MONTH);
                int year = Calendar.getInstance().get(Calendar.YEAR);

                final String x = String.valueOf(hur + ":" + minute + ":00");
                final String z = String.valueOf( year + "-" + month  + "-" +day);

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://149.202.92.150/projekt/rest/dodaniewyktreningu.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obiekt= new JSONObject(response);
                                    id_tren = obiekt.getString("id_treningu");
                                    System.out.println("ID_T: " + id_tren);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(getApplicationContext(),TreningCwiczenieWyborActivity.class);
                                long numer = id+1;
                                intent.putExtra("id_treningu",id_tren);
                                intent.putExtra("numerT",numer);
                                startActivity(intent);

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
                        params.put("godzina_wykonania",x);
                        params.put("data_wykonania",z);
                        params.put("id_uzytkownika_fk","1");
                        System.out.println("ID: " + listaCwiczen.get((int)id).get("id_treningu_pk"));
                        params.put("id_treningu_fk",listaCwiczen.get((int)id).get("id_treningu_pk"));


                        return params;
                    }};

                queue.add(stringRequest);
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
