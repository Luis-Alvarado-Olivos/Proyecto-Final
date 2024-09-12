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

public class CocinaActivity extends AppCompatActivity {

    private Button buttonBack;
    TextView tc_texto;
    TextView tg_txt;
    private DatabaseReference mDatabase;

    private ToggleButton toggleButton3;
    private ToggleButton toggleButton4;
    private int hc;
    private int hd;
    private ImageView luz;
    private ImageView gas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocina);


        buttonBack = findViewById(R.id.buttonBack2);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la actividad anterior
            }
        });


        tc_texto = (TextView) findViewById(R.id.tc_texto);
        tg_txt = (TextView) findViewById(R.id.tg_txt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toggleButton3 = findViewById(R.id.toggleButton3);
        toggleButton4 = findViewById(R.id.toggleButton4);
        luz = findViewById(R.id.imageView5);
        gas = findViewById(R.id.imageView6);

        //vaina 1

        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hc==0) {
                    luz.setImageResource(R.drawable.foco_apagado);
                } else {
                    luz.setImageResource(R.drawable.foco_encendido);
                }

            }
        });

        toggleButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hd==0) {
                    gas.setImageResource(R.drawable.gas_cerrado);
                } else {
                    gas.setImageResource(R.drawable.gas_encendido);
                }

            }
        });

        mDatabase.child("cocina").child("LuzCocina").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hc = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tc_texto.setText(String.valueOf(hc));
                    toggleButton3.setChecked(hc == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hc = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("cocina").child("LuzCocina").setValue(hc);
        });





        //vaina 2

        mDatabase.child("cocina").child("GasCocina").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hd = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    tg_txt.setText(String.valueOf(hd));
                    toggleButton4.setChecked(hd == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hd = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("cocina").child("GasCocina").setValue(hd);
        });
    }
}
