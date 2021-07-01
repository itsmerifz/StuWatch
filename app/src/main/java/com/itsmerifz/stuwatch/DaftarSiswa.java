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
  public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()){
      case R.id.detSiswa:
        Bundle b = new Bundle();
        b.putString("nm",nama);
        b.putString("kls",kls);
        b.putString("nis",nis);
        Intent i = new Intent(getApplicationContext(),DetailSiswa.class);
        i.putExtras(b);
        startActivity(i);
        break;
      case R.id.edSiswa:
        siswaArrayList.clear();
        Bundle b2 = new Bundle();
        b2.putString("nm",nama);
        b2.putString("kls",kls);
        b2.putString("nis",nis);
        b2.putString("kd",kode);
        Intent in = new Intent(getApplicationContext(),EditSiswa.class);
        in.putExtras(b2);
        startActivity(in);
        break;
      case R.id.hpsSiswa:
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Apakah yakin "+nama+" akan dihapus?");
        alert.setMessage("Tekan 'ya' untuk menghapus")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    hapusSiswa(kode);
                    Toast.makeText(getApplicationContext(),nama+" telah dihapus",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),DaftarSiswa.class));
                  }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });
        AlertDialog ad = alert.create();
        ad.show();
        break;
    }
    return false;
  }

  private void hapusSiswa(String kode) {
    if (db != null){
      db.child("siswa").child(kode).removeValue();
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

