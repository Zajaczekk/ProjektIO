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

public class CwiczeniaTreningActivity extends AppCompatActivity {

    ListView cwiczeniaLV;
    public ArrayList<Map<String,String>> listaCwiczen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwiczenia_trening);

        cwiczeniaLV = (ListView)findViewById(R.id.lvCwT);
        final long value = getIntent().getLongExtra("value",1);
        listaCwiczen = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://149.202.92.150/projekt/rest/treningtocwiczen.php?id=" + String.valueOf(value);

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
                Intent intent = new Intent(getApplicationContext(),CwiczeniePlanowanieActivity.class);
                intent.putExtra("value",id);
                intent.putExtra("trening",value);
                startActivity(intent);
            }
        });
    }
}
;