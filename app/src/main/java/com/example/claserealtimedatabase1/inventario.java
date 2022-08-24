package com.example.claserealtimedatabase1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class inventario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuinventario,
                menu);
        return true;
    }

    @Override
    public boolean
    onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Ventas) {
            Intent a = new Intent(this, venta.class
            );
            startActivity(a);
            Toast.makeText(this, "Bienvenido",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.Compras) {
            Intent a = new Intent(this, compras.class
            );
            startActivity(a);
            Toast.makeText(this, "Bienvenido",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.Lista_de_productos) {
            Intent a = new Intent(this, lista.class
            );
            startActivity(a);
            Toast.makeText(this, "Bienvenido",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.Cerrar_sesión) {
            Intent a = new Intent(this, LoginActivity.class
            );
            startActivity(a);
            Toast.makeText(this, "Bienvenido",
                    Toast.LENGTH_LONG).show();
            return true;

        } return
                super.onOptionsItemSelected(item);
    }
}