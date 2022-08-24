package com.example.claserealtimedatabase1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class lista extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";

    private TextView lblCodigo;
    private TextView lblNombre;
    private TextView lblStock;
    private TextView lblVenta;
    private Button btnEliminarListener;

    private DatabaseReference dbCielo;
    private DatabaseReference dbPrediccion;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lblCodigo = (TextView)findViewById(R.id.lblCodigo);
        lblNombre = (TextView)findViewById(R.id.lblNombre);
        lblStock = (TextView)findViewById(R.id.lblStock);
        lblVenta = (TextView)findViewById(R.id.lblVenta);
        btnEliminarListener = (Button)findViewById(R.id.btnEliminarListener);

            /*
            dbCielo =
                FirebaseDatabase.getInstance().getReference()
                    .child("prediccion-hoy")
                        .child("cielo");

            dbCielo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String valor = dataSnapshot.getValue().toString();
                    lblCielo.setText(valor);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAGLOG, "Error!", databaseError.toException());
                }
            });
            */

        dbPrediccion =
                FirebaseDatabase.getInstance().getReference()
                        .child("tienda");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Opción 1
                //lblCielo.setText(dataSnapshot.child("cielo").getValue().toString());
                //lblTemperatura.setText(dataSnapshot.child("temperatura").getValue().toString());
                //lblHumedad.setText(dataSnapshot.child("humedad").getValue().toString());

                //Opcion 2
                Prediccion pred = dataSnapshot.getValue(Prediccion.class);
                lblCodigo.setText((int) pred.getCodigo() + " ");
                lblNombre.setText(pred.getNombre() + " ");
                lblStock.setText(pred.getStock() + " #");
                lblVenta.setText(pred.getVenta() + " $");

                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        dbPrediccion.addValueEventListener(eventListener);

        btnEliminarListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbPrediccion.removeEventListener(eventListener);
            }
        });
    }

}