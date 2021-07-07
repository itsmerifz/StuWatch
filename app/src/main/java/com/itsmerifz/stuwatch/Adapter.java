package com.itsmerifz.stuwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

  // Deklarasi Variabel
  List<String> judul;
  List<Integer> gambar;
  LayoutInflater inflater;

  public Adapter(Context c, List<String> jd, List<Integer> gmb){ // Konstruktor Adapter
    this.judul = jd;
    this.gambar = gmb;
    this.inflater = LayoutInflater.from(c);
  }


  @NonNull
  @Override
  // Menampilkan layout yang di inflate
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.card_menu_layout,parent,false);
    return new ViewHolder(view);
  }

  @Override
  // Set tampilan dari adpater
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.judul.setText(judul.get(position));
    holder.icon.setImageResource(gambar.get(position));
  }

  @Override
  // Menghitung jumlah dari item yang ada pada adapter
  public int getItemCount() {
    return judul.size();
  }

  // Membuat kelas ViewHolder
  public class ViewHolder extends RecyclerView.ViewHolder{
    TextView judul;
    ImageView icon;

    public ViewHolder(@NonNull View itemView) { // Konstruktor ViewHolder
      super(itemView);
      judul = itemView.findViewById(R.id.jMenu);
      icon = itemView.findViewById(R.id.imageView);

      // Kondisi jika card diklik
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          switch (getAdapterPosition()){
            case 0:
              itemView.getContext().startActivity( new Intent(Adapter.this.inflater.getContext(), EditProfil.class));
              break;

            case 1:
              itemView.getContext().startActivity(new Intent(Adapter.this.inflater.getContext(),DaftarSiswa.class));
              break;

            case 2:
              itemView.getContext().startActivity(new Intent(Adapter.this.inflater.getContext(),InputDataa.class));
              break;

            case 3:
              itemView.getContext().startActivity(new Intent(Adapter.this.inflater.getContext(),HasilSiswa.class));
          }
        }
      });
    }
  }
}
