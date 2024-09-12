package com.example.gymapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class BathActivity extends AppCompatActivity {

    private Button buttonBack3;
    TextView tb_texto;
    TextView tgr_txt;
    private DatabaseReference mDatabase;

    private ToggleButton toggleButton5;
    private ToggleButton toggleButton6;
    private int he;
    private int hf;
    private ImageView luz;
    private ImageView grifo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bath);

        buttonBack3 = findViewById(R.id.buttonBack3);

        buttonBack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la actividad anterior
            }
        });


        tb_texto = (TextView) findViewById(R.id.tb_texto);
        tgr_txt = (TextView) findViewById(R.id.tgr_txt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toggleButton5 = findViewById(R.id.toggleButton5);
        toggleButton6 = findViewById(R.id.toggleButton6);
        luz = findViewById(R.id.imageView2);
        grifo = findViewById(R.id.imageView4);
        //vaina 1

        toggleButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (he==0) {
                    luz.setImageResource(R.drawable.foco_apagado);
                } else {
                    luz.setImageResource(R.drawable.foco_encendido);
                }

            }
        });

        toggleButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hf==0) {
                    grifo.setImageResource(R.drawable.grifo_cerrado);
                } else {
                    grifo.setImageResource(R.drawable.grifo_abierto);
                }

            }
        });





        mDatabase.child("bath").child("LuzBath").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    he = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tb_texto.setText(String.valueOf(he));
                    toggleButton5.setChecked(he == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            he = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("bath").child("LuzBath").setValue(he);
        });





        //vaina 2

        mDatabase.child("bath").child("GrifoBath").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hf = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tgr_txt.setText(String.valueOf(hf));
                    toggleButton6.setChecked(hf == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hf = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("bath").child("GrifoBath").setValue(hf);
        });

}
}
