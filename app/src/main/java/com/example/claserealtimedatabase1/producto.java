package com.example.claserealtimedatabase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class  producto extends AppCompatActivity {

    private List<producto1> listPerson = new ArrayList<producto1>();
    ArrayAdapter<producto1> arrayAdapterPersona;


    EditText codigoP, productoP, stockP, costoP, ventaP;
    ListView listV_personas;
    Button btnAgregar, btnEliminar, btnActualizar;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

        producto1 personaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        codigoP = findViewById(R.id.etCodigo);
        productoP = findViewById(R.id.etProducto);
        stockP = findViewById(R.id.etStock);
        costoP = findViewById(R.id.etCosto);
        ventaP = findViewById(R.id.etVenta);

        listV_personas = findViewById(R.id.lv_datosProductos);
        inicializarFirebase();
        listarDatos();

        listV_personas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSelected = (producto1) parent.getItemAtPosition(position);
                codigoP.setText(personaSelected.getCodigo());
                productoP.setText(personaSelected.getProducto());
                stockP.setText(personaSelected.getStock());
                costoP.setText(personaSelected.getCosto());
                ventaP.setText(personaSelected.getVenta());
            }
        });



    }
    private void listarDatos() {
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPerson.clear();
                for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    producto1 p = objSnaptshot.getValue(producto1.class);
                    listPerson.add(p);

                    arrayAdapterPersona = new ArrayAdapter<producto1>(producto.this, android.R.layout.simple_list_item_1, listPerson);
                    listV_personas.setAdapter(arrayAdapterPersona);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuproducto,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String codigo = codigoP.getText().toString();
        String producto = productoP.getText().toString();
        String stock = stockP.getText().toString();
        String costo = costoP.getText().toString();
        String venta = ventaP.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_add1:{
                //Toast.makeText(this,"Agregar", Toast.LENGTH_LONG).show();
                if (codigo.equals("")||producto.equals("")||stock.equals("")||costo.equals("")||venta.equals("")){
                    validacion();
                }
                else {
                    producto1 p = new producto1();
                    p.setUid(UUID.randomUUID().toString());
                    p.setCodigo(codigo);
                    p.setProducto(producto);
                    p.setStock(stock);
                    p.setCosto(costo);
                    p.setVenta(venta);

                    databaseReference.child("Producto").child(p.getUid()).setValue(p);
                    Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    limpiarCajas();
                }
                break;
            }
            case R.id.icon_save1:{
                producto1 p = new producto1();
                p.setUid(personaSelected.getUid());
                p.setCodigo(codigoP.getText().toString().trim());
                p.setProducto(productoP.getText().toString().trim());
                p.setStock(stockP.getText().toString().trim());
                p.setCosto(costoP.getText().toString().trim());
                p.setVenta(ventaP.getText().toString().trim());
                databaseReference.child("Producto").child(p.getUid()).setValue(p);
                Toast.makeText(this,"Actualizado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            case R.id.icon_delete1:{
                producto1 p = new producto1();
                p.setUid(personaSelected.getUid());
                databaseReference.child("Producto").child(p.getUid()).removeValue();
                Toast.makeText(this,"Eliminado", Toast.LENGTH_LONG).show();
                limpiarCajas();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiarCajas() {
        codigoP.setText("");
        productoP.setText("");
        stockP.setText("");
        costoP.setText("");
        ventaP.setText("");

    }

    private void validacion() {
        String codigo = codigoP.getText().toString();
        String producto = productoP.getText().toString();
        String stock = stockP.getText().toString();
        String costo = costoP.getText().toString();
        String venta = ventaP.getText().toString();


        if (codigo.equals("")) {
            codigoP.setError("Required");
        } else if (producto.equals("")) {
            productoP.setError("Required");
        } else if (stock.equals("")) {
            stockP.setError("Required");
        } else if (costo.equals("")) {
            costoP.setError("Required");
        } else if (venta.equals("")) {
            ventaP.setError("Required");
        }
    }

    public void continuar1(View view) {
        Intent i = new Intent(this, inventario.class);
        startActivity(i);
    }
}