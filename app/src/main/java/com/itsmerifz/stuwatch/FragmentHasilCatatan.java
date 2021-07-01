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

public class FragmentHasilCatatan extends Fragment {
  TextView catatan,nama;
  DatabaseReference db;
  public FragmentHasilCatatan() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_hasil_catatan, container, false);
    catatan = v.findViewById(R.id.hsCatatan);
    nama = v.findViewById(R.id.tv_namaS);
    nama.setText("Nama     :   "+MenuHasilSiswa.nama);
    db = FirebaseDatabase.getInstance().getReference();
    db.child("siswa").child(MenuHasilSiswa.kode).child("nilai").child("catatan").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){
          Nilai n = ds.getValue(Nilai.class);
          catatan.setText(n.getCatatan());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    return v;
  }
}