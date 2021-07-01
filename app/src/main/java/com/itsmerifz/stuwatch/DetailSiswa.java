package com.itsmerifz.stuwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class DetailSiswa extends AppCompatActivity {
  TextView detNama,detKls,detNIS;
  String nm,kls,nis;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_siswa);
    detNama = findViewById(R.id.detNamaS);
    detKls = findViewById(R.id.detKelasS);
    detNIS = findViewById(R.id.detNisS);

    Bundle b = getIntent().getExtras();
    nm = b.getString("nm");
    kls = b.getString("kls");
    nis = b.getString("nis");

    detNama.setText(nm);
    detNIS.setText(nis);
    detKls.setText(kls);
  }
}