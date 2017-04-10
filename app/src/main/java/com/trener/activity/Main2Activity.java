package com.trener.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import info.androidhive.loginandregistration.R;

public class Main2Activity extends AppCompatActivity{

    Button btnLogowanie, btnCwiczenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnLogowanie = (Button)findViewById(R.id.btnLogowanie);
        btnCwiczenia = (Button)findViewById(R.id.btnCwiczenia);

        btnLogowanie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }

        });
    }
}
