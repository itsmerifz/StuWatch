package com.itsmerifz.stuwatch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentHasilPengetahuan extends Fragment {
  // Deklarasi variabel
  TextView nama,ipa,ips,agm,senbud,penjas,pkn,indo,inggris,rata;
  DatabaseReference db;
  public FragmentHasilPengetahuan() { // Konstruktor Fragment
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate layout kedalam fragment
    View v = inflater.inflate(R.layout.fragment_hasil_pengetahuan, container, false);
    db = FirebaseDatabase.getInstance().getReference(); // Get Firebase Realtime Database
    nama = v.findViewById(R.id.tv_namaS);
    ipa = v.findViewById(R.id.hsIPA);
    ips = v.findViewById(R.id.hsIPS);
    agm = v.findViewById(R.id.hsAgm);
    senbud = v.findViewById(R.id.hsSenbud);
    penjas = v.findViewById(R.id.hsPenjas);
    pkn = v.findViewById(R.id.hsPkn);
    indo = v.findViewById(R.id.hsIndo);
    inggris = v.findViewById(R.id.hsIngg);
    rata = v.findViewById(R.id.hsRata);
    nama.setText("Nama     :   "+MenuHasilSiswa.nama);

    // Get data dari database
    db.child("siswa").child(MenuHasilSiswa.kode).child("nilai").child("pengetahuan").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){
          Nilai n = ds.getValue(Nilai.class);
          ipa.setText(n.getIpa());
          ips.setText(n.getIps());
          agm.setText(n.getAgama());
          pkn.setText(n.getPkn());
          inggris.setText(n.getInggris());
          indo.setText(n.getIndo());
          senbud.setText(n.getSenbud());
          penjas.setText(n.getPenjas());
          rata.setText(n.getRata());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    return v;
  }
}