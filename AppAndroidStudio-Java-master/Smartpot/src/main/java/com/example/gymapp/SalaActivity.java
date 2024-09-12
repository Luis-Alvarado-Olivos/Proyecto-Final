package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SalaActivity extends AppCompatActivity {


    private Button buttonBack4;
    TextView ts_texto;
    TextView tgB_txt;
    TextView tp_txt;
    private DatabaseReference mDatabase;

    private ToggleButton toggleButton7;
    private ToggleButton toggleButton8;
    private ToggleButton toggleButton9;
    private ImageView balconCerrado;
    private ImageView puertaCerrada;
    private ImageView focoApagado1;
    private int hg;
    private int hh;
    private int hi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        buttonBack4 = findViewById(R.id.buttonBack4);

        buttonBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la actividad anterior
            }
        });


        ts_texto = (TextView) findViewById(R.id.ts_texto);
        tgB_txt = (TextView) findViewById(R.id.tgB_txt);
        tp_txt = (TextView) findViewById(R.id.tp_txt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toggleButton7 = findViewById(R.id.toggleButton7);
        toggleButton8 = findViewById(R.id.toggleButton8);
        toggleButton9 = findViewById(R.id.toggleButton9);
        focoApagado1 = findViewById(R.id.FocoApagadoSala);
        balconCerrado = findViewById(R.id.BalconCerradoSala);
        puertaCerrada = findViewById(R.id.PuertaCerradaSala);



        // Estado Sala Luz

        toggleButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hg==0) {
                    focoApagado1.setImageResource(R.drawable.foco_apagado);
                } else {
                    focoApagado1.setImageResource(R.drawable.foco_encendido);
                }

            }
        });



        mDatabase.child("sala").child("LuzSala").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hg = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    ts_texto.setText(String.valueOf(hg));
                    toggleButton7.setChecked(hg == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hg = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("sala").child("LuzSala").setValue(hg);
        });





        // Estado Sala Balcon


        toggleButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hh==0) {
                    balconCerrado.setImageResource(R.drawable.balcon_cerrado);
                } else {
                    balconCerrado.setImageResource(R.drawable.balcon_abierto);
                }

            }
        });



        mDatabase.child("sala").child("BalconSala").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hh = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tgB_txt.setText(String.valueOf(hh));
                    toggleButton8.setChecked(hh == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton8.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hh = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("sala").child("BalconSala").setValue(hh);
        });

        // Estado Sala Puerta

        toggleButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hi==0) {
                    puertaCerrada.setImageResource(R.drawable.closed_door);
                } else {
                    puertaCerrada.setImageResource(R.drawable.open_door);
                }

            }
        });





        mDatabase.child("sala").child("puerta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hi = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tp_txt.setText(String.valueOf(hi));
                    toggleButton9.setChecked(hi == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton9.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hi = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("sala").child("puerta").setValue(hi);
        });
    }
}