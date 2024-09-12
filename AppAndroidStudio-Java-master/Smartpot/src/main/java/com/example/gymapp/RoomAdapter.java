package com.example.gymapp;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<String> roomList;
    private Context context;

    public RoomAdapter(List<String> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        String roomName = roomList.get(position);
        holder.buttonRoom.setText(roomName);  // Cambia el texto del botón al nombre de la habitación
        holder.textRoomName.setText("Nombre: " + roomName); // Muestra el nombre en el TextView

        // Listener para el botón de eliminar habitación
        holder.buttonDeleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();  // Obtén la posición actual
                if (currentPosition != RecyclerView.NO_POSITION) {  // Verifica que la posición es válida
                    roomList.remove(currentPosition);  // Elimina el elemento de la lista
                    notifyItemRemoved(currentPosition);  // Notifica al RecyclerView que se ha eliminado un elemento
                    Toast.makeText(context, "Habitación eliminada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {

        Button buttonDeleteRoom;
        Button buttonRoom;
        TextView textRoomName; // Declara el TextView

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonRoom = itemView.findViewById(R.id.buttonRoom);
            textRoomName = itemView.findViewById(R.id.textRoomName); // Enlaza el TextView
            buttonDeleteRoom = itemView.findViewById(R.id.buttonDeleteRoom); // Enlaza el botón de eliminar habitación
        }
    }
}