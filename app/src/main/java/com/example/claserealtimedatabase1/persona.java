package com.example.claserealtimedatabase1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class persona extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona);
    }
    public void continuar(View view) {
        Intent i = new Intent(this, inventario.class);
        startActivity(i);
    }
}