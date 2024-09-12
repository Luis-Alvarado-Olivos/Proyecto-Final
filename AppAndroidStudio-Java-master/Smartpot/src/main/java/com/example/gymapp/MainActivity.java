package com.example.gymapp;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonUpperBody, buttonLowerBody, button1, button2, button3, buttonAddRoom, buttonColor;
    private TextView textViewUsername;
    private FirebaseAuth mAuth;

    // Código para agregar habitaciones
    private RecyclerView recyclerViewRooms;
    private RoomAdapter roomAdapter;
    private List<String> roomList;

    // SharedPreferences para almacenar el color
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Cargar el color guardado al iniciar la app
        int savedColor = sharedPreferences.getInt("selectedColor", Color.WHITE); // Blanco como valor por defecto
        getWindow().getDecorView().setBackgroundColor(savedColor);

        // Código botón agregar habitaciones
        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        buttonUpperBody = findViewById(R.id.buttonUpperBody);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);


        // Listener para cambiar el nombre de la habitación con un LongClickListener
        setupLongClickForRename(buttonUpperBody);
        setupLongClickForRename(button1);
        setupLongClickForRename(button2);
        setupLongClickForRename(button3);

        // Inicializar lista de habitaciones
        roomList = new ArrayList<>();

        // Configurar RecyclerView
        roomAdapter = new RoomAdapter(roomList, this);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRooms.setAdapter(roomAdapter);

        // Configurar botón para agregar nuevas habitaciones
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddRoomDialog();
            }
        });


        // Listeners para abrir nuevas actividades al hacer click
        buttonUpperBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CuartoActivity.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SalaActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BathActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CocinaActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para mostrar el diálogo para agregar una nueva habitación
    private void showAddRoomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar Habitación");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String roomName = input.getText().toString();
                if (!roomName.isEmpty()) {
                    roomList.add(roomName);
                    roomAdapter.notifyItemInserted(roomList.size() - 1);
                    Toast.makeText(MainActivity.this, "Habitación agregada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    // Método para configurar LongClickListener en cada botón
    private void setupLongClickForRename(Button button) {
        button.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Cambiar nombre de habitación");

            // Configurar un EditText en el diálogo
            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(button.getText()); // Mostrar el nombre actual
            builder.setView(input);

            // Configurar los botones del diálogo
            builder.setPositiveButton("OK", (dialog, which) -> {
                // Cambiar el texto del botón al nuevo nombre
                button.setText(input.getText().toString());
            });

            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            // Mostrar el diálogo
            builder.show();
            return true;
        });
    }

}