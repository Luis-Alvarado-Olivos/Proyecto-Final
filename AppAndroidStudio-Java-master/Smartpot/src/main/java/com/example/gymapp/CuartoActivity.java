package com.example.gymapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
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
import androidx.core.app.NotificationCompat;


public class CuartoActivity extends AppCompatActivity {


    private Button buttonBack;
    TextView tv_texto;
    TextView th_texto;
    TextView luz_txt;
    TextView ts_txt;
    private DatabaseReference mDatabase;

    private ToggleButton toggleButton1;
    private ToggleButton toggleButton2;
    private int ha;
    private int hb;
    private ImageView focoApagado;
    private ImageView windowclosed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuarto);



        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad actual y regresa a la actividad anterior
            }
        });


        th_texto = (TextView) findViewById(R.id.th_texto);
        ts_txt = (TextView) findViewById(R.id.ts_txt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        focoApagado = findViewById(R.id.FocoApagado);
        windowclosed = findViewById(R.id.WindowClosed);




        //ToggleButton1 (Luz Cuarto)

        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ha==0) {
                    focoApagado.setImageResource(R.drawable.foco_apagado);
                } else {
                    focoApagado.setImageResource(R.drawable.foco_encendido);
                }

            }
        });


        mDatabase.child("dormitorio").child("LuzCuarto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    ha = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    th_texto.setText(String.valueOf(ha));
                    toggleButton1.setChecked(ha == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            ha = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("dormitorio").child("LuzCuarto").setValue(ha);
        });





        //ToggleButton2 (Estado ventana)

        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hb==0) {
                    windowclosed.setImageResource(R.drawable.closed_window);
                } else {
                    windowclosed.setImageResource(R.drawable.open_window);
                }

            }
        });

        mDatabase.child("dormitorio").child("VentanaCuarto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener y actualizar el valor de 'ha'
                    hb = Integer.parseInt(dataSnapshot.getValue().toString());

                    // Actualizar el TextView y el estado del ToggleButton
                    ts_txt.setText(String.valueOf(hb));
                    toggleButton2.setChecked(hb == 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });

        // Listener para manejar los cambios de estado del ToggleButton
        toggleButton2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizar la variable 'ha' según el estado del ToggleButton
            hb = isChecked ? 1 : 0;

            // Actualizar el valor de "luz cuarto" en Firebase
            mDatabase.child("dormitorio").child("VentanaCuarto").setValue(hb);
        });







    }

    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Verifica si se necesita crear un canal de notificación para versiones de Android 8.0 y superiores
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default_channel_id", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Crea una notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default_channel_id")
                .setSmallIcon(R.drawable.smth)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Muestra la notificación
        notificationManager.notify(1, builder.build());
    }

}




