package com.itsmerifz.stuwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class DetailSiswa extends AppCompatActivity {
  // Deklarasi variabel
  TextView detNama,detKls,detNIS;
  String nm,kls,nis;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_siswa);
    detNama = findViewById(R.id.detNamaS);
    detKls = findViewById(R.id.detKelasS);
    detNIS = findViewById(R.id.detNisS);

    Bundle b = getIntent().getExtras(); // Get bundle dari intent
    // Mengambil data string dari bundle
    nm = b.getString("nm");
    kls = b.getString("kls");
    nis = b.getString("nis");

    // Set text dari data string
    detNama.setText(nm);
    detNIS.setText(nis);
    detKls.setText(kls);
  }
}