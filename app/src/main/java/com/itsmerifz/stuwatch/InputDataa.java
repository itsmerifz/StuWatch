package com.itsmerifz.stuwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InputDataa extends AppCompatActivity {
  AdapterSiswa adapter;
  private ListView list;
  String nama,nis,kls,kode;
  public static ArrayList<Siswa> siswaArrayList = new ArrayList<>();
  private DatabaseReference db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_daftar_siswa);

    db = FirebaseDatabase.getInstance().getReference();
    db.child("siswa").orderByChild("nama").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){
          Siswa s = ds.getValue(Siswa.class);
          s.setKode(ds.getKey());
          siswaArrayList.add(s);
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        System.out.println(error.getDetails()+" "+error.getMessage());
      }
    });

    list = findViewById(R.id.listSiswa);
    siswaArrayList = new ArrayList<>();

    while (true) {
      AdapterSiswa la = new AdapterSiswa(this);
      adapter = la;
      list.setAdapter(la);
      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int p, long id) {
          nama = siswaArrayList.get(p).getNama();
          nis = siswaArrayList.get(p).getNis();
          kls = siswaArrayList.get(p).getKelas();
          kode = siswaArrayList.get(p).getKode();
          Bundle b = new Bundle();
          b.putString("nama",nama);
          b.putString("nis",nis);
          b.putString("kd",kode);
          Intent i = new Intent(getApplicationContext(),MenuInputData.class);
          i.putExtras(b);
          startActivity(i);
        }
      });
      return;
    }
  }
//  AdapterListInputSiswa adapter;
//  private ListView list;
//  String nm,nis,kode;
//  public static ArrayList<Siswa> siswas = new ArrayList<>();
//  private DatabaseReference db;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_input_data);
//    db = FirebaseDatabase.getInstance().getReference();
//    db.child("siswa").orderByChild("nama").addValueEventListener(new ValueEventListener() {
//      @Override
//      public void onDataChange(@NonNull DataSnapshot snapshot) {
//        for (DataSnapshot ds : snapshot.getChildren()){
//          Siswa s = ds.getValue(Siswa.class);
//          s.setKode(ds.getKey());
//          siswas.add(s);
//        }
//      }
//
//      @Override
//      public void onCancelled(@NonNull DatabaseError error) {
//        System.out.println(error.getDetails()+" "+error.getMessage());
//      }
//    });
//
//    list = findViewById(R.id.listSiswa2);
//    siswas = new ArrayList<>();
//
//    while (true){
//      AdapterListInputSiswa la = new AdapterListInputSiswa(this);
//      adapter = la;
//      list.setAdapter(la);
//      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////          Bundle b = new Bundle();
////          nm = siswas.get(position).getNama();
////          nis = siswas.get(position).getNis();
////          kode = siswas.get(position).getKode();
////          b.putString("nama",nm);
////          b.putString("nis",nis);
////          b.putString("kd",kode);
////          Intent i = new Intent(getApplicationContext(),MenuInputData.class);
////          i.putExtras(b);
////          startActivity(i);
//        }
//      });
//    }
//  }
}