package com.itsmerifz.stuwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahSiswa extends AppCompatActivity {
  EditText edNama,edKelas,edNis;
  Button btnAddSiswa;
  TextInputLayout edNamaErr,edKelasErr,edNisErr;
  ProgressBar pb;
  DatabaseReference db;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tambah_siswa);
    edNama = findViewById(R.id.txNamaS);
    edKelas = findViewById(R.id.txKelasS);
    edNis = findViewById(R.id.txNIS);
    btnAddSiswa = findViewById(R.id.btnDaftarS);
    edNamaErr = findViewById(R.id.txNamaSErr);
    edKelasErr = findViewById(R.id.txKelasSErr);
    edNisErr = findViewById(R.id.txNISErr);
    pb = findViewById(R.id.pbDaftarS);
    db = FirebaseDatabase.getInstance().getReference();

    btnAddSiswa.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String nama,kelas,nis;
        nama = edNama.getText().toString();
        kelas = edKelas.getText().toString();
        nis = edNis.getText().toString();
        pb.setVisibility(View.VISIBLE);
        if (isValid()){
          submitDataSiswa(new Siswa(nama,kelas,nis));
        }
        pb.setVisibility(View.GONE);
      }
    });
  }

  public boolean isValid(){
    String nama,kelas,nis;
    nama = edNama.getText().toString();
    kelas = edKelas.getText().toString();
    nis = edNis.getText().toString();
    if (nama.isEmpty() && kelas.isEmpty() && nis.isEmpty()){
      Toast.makeText(getApplicationContext(),"Semua wajib diisi!",Toast.LENGTH_SHORT).show();
      return false;
    }else if (nama.isEmpty()){
      edNamaErr.setError("Nama Wajib Diisi!");
      return false;
    }else if (kelas.isEmpty()){
      edKelasErr.setError("Kelas Wajib Diisi!");
      return false;
    }else if(nis.isEmpty()){
      edNisErr.setError("NIS Wajib Diisi!");
      return false;
    }else if (nis.length() < 4){
      edNisErr.setError("NIS Minimal 4 Karakter!");
      return false;
    }
    return true;
  }

  private void submitDataSiswa(Siswa siswa){
    db.child("siswa").push().setValue(siswa).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void e) {
        edNama.setText("");
        edKelas.setText("");
        edNis.setText("");
        Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan!",Toast.LENGTH_LONG).show();
      }
    });
  }
}