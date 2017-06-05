package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.Map;

import info.androidhive.loginandregistration.R;

public class CwiczeniePlanowanieActivity extends AppCompatActivity {

    LinearLayout ll;
    EditText serieET, dystansET, ciezarET, powtET;
    TextView tv1, tv2;
    Button btnZaplanuj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cwiczenie_planowanie);

        final String[] idcw = new String[1];
        final long value = getIntent().getLongExtra("value",1);
        final long trening = getIntent().getLongExtra("trening",1);
        serieET = (EditText) findViewById(R.id.editText4);
        dystansET = (EditText) findViewById(R.id.editText3);
        ciezarET = (EditText) findViewById(R.id.editText11);
        powtET = (EditText) findViewById(R.id.editText10);
        btnZaplanuj = (Button) findViewById(R.id.btnZaplanuj);
        tv1 = (TextView) findViewById(R.id.textView4);
        tv2 = (TextView) findViewById(R.id.textView5);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="http://149.202.92.150/projekt/rest/treningtocwiczen.php?id=" + String.valueOf(trening);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray cwiczenia = new JSONArray(response);
                            JSONObject c = cwiczenia.getJSONObject((int)value);

                            String nazwa = c.getString("nazwa_c");
                            String powt = c.getString("ilosc_pow");
                            String dyst = c.getString("dystans");
                            String serie = c.getString("ilosc_serii");
                            String ciezar = c.getString("ciezar");
                            idcw[0] = c.getString("id_cwiczenia_pk");

                            HashMap<String,String> cwiczenie = new HashMap<>();
                            cwiczenie.put("nazwa_c",nazwa);
                            cwiczenie.put("ilosc_pow",powt);
                            cwiczenie.put("dystans",dyst);
                            cwiczenie.put("ilosc_serii",serie);
                            cwiczenie.put("ciezar",ciezar);

                            System.out.println(powt + " | " + serie + " | " + dyst + " | " + ciezar + " | " + url);

                            if (serie.equals("1")){
                                serieET.setEnabled(true);
                            }
                            else{
                                serieET.setEnabled(false);
                            }

                            if (powt.equals("1")){
                                powtET.setEnabled(true);
                            }
                            else{
                                powtET.setEnabled(false);
                            }

                            if (ciezar.equals("1")){
                                ciezarET.setEnabled(true);
                            }
                            else{
                                ciezarET.setEnabled(false);
                            }

                            if (dyst.equals("1")){
                                dystansET.setEnabled(true);
                            }
                            else{
                                dystansET.setEnabled(false);
                            }

                            tv1.append(nazwa);

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

        btnZaplanuj.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                String url = "http://149.202.92.150/projekt/rest/dodanieplanucwiczenia.php";

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
                        params.put("id_uzytkownika_fk","1");
                        params.put("id_treningu_fk",String.valueOf(trening));
                        long vcw = value+1;
                        params.put("id_cwiczenia_fk", idcw[0]);
                        params.put("ilosc_serii",serieET.getText().toString());
                        params.put("ilosc_pow",powtET.getText().toString());
                        params.put("dystans",dystansET.getText().toString());
                        params.put("ciezar",ciezarET.getText().toString());

                        return params;
                    }};

                queue.add(stringRequest);

                Toast.makeText(getApplicationContext(),"Dodano.",Toast.LENGTH_SHORT);

                Intent i = new Intent(getApplicationContext(),
                        CwiczeniaActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
