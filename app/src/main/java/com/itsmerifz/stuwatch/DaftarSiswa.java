package com.itsmerifz.stuwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class  DaftarSiswa extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
// Deklarasi Variabel
  AdapterSiswa adapter;
  private ListView list;
  String nama,nis,kls,kode;
  public static ArrayList<Siswa> siswaArrayList = new ArrayList<>();
  private DatabaseReference db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_daftar_siswa); // Set layout activity

    db = FirebaseDatabase.getInstance().getReference(); // Set Firebase Realtime Database
    db.child("siswa").orderByChild("nama").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){ // Get data dari children database yang dituju
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

    // Set tampilan dari adapter yang dituju
    while (true) {
      AdapterSiswa la = new AdapterSiswa(this);
      adapter = la;
      list.setAdapter(la);
      // Kondisi ketika daftar list diklik
      list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int p, long id) {
          // Get data dari arraylist
          nama = siswaArrayList.get(p).getNama();
          nis = siswaArrayList.get(p).getNis();
          kls = siswaArrayList.get(p).getKelas();
          kode = siswaArrayList.get(p).getKode();

          PopupMenu pm = new PopupMenu(getApplicationContext(), v);
          pm.setOnMenuItemClickListener(DaftarSiswa.this);
          pm.inflate(R.menu.popup_menu);
          pm.show();
        }
      });
      return;
    }
  }

  @Override
  public boolean onMenuItemClick(MenuItem item) { // Kondisi ketika menu item diklik
    switch (item.getItemId()){
      case R.id.detSiswa:
        Bundle b = new Bundle(); // Make bundle
        // Set string dari bundle
        b.putString("nm",nama);
        b.putString("kls",kls);
        b.putString("nis",nis);
        Intent i = new Intent(getApplicationContext(),DetailSiswa.class); // Make Intent
        i.putExtras(b); // Masukkan hasil data bundle kedalam intent
        startActivity(i);
        break;
      case R.id.edSiswa:
        siswaArrayList.clear();
        Bundle b2 = new Bundle(); // Make bundle
        // Set string dari bundle
        b2.putString("nm",nama);
        b2.putString("kls",kls);
        b2.putString("nis",nis);
        b2.putString("kd",kode);
        Intent in = new Intent(getApplicationContext(),EditSiswa.class); // Make intent
        in.putExtras(b2); // Masukkan hasil data bundle kedalam intent
        startActivity(in);
        break;
      case R.id.hpsSiswa:
        // Membuat alert
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Apakah yakin "+nama+" akan dihapus?");
        alert.setMessage("Tekan 'ya' untuk menghapus")
                .setCancelable(false)
                // Kondisi jika tombol ya diklik
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    hapusSiswa(kode);
                    Toast.makeText(getApplicationContext(),nama+" telah dihapus",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),DaftarSiswa.class));
                  }
                })
                // Kondisi jika tombol tidak diklik
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        AlertDialog ad = alert.create();
        ad.show(); // Menampilkan alert yang dibuat
        break;
    }
    return false;
  }

  // Method hapus data siswa
  private void hapusSiswa(String kode) {
    if (db != null){ // Cek kondisi jika database tidak null
      db.child("siswa").child(kode).removeValue(); // Hapus data dari child siswa
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_siswa, menu);
    return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.addSiswa){
      startActivity(new Intent(getApplicationContext(),TambahSiswa.class));
    }
    return true;
  }

}

