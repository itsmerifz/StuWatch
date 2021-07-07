package com.itsmerifz.stuwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfil extends AppCompatActivity {
  // Deklarasi variabel
  public static final String TAG = "TAG";
  String nm,almt,mail;
  EditText edNama,edAlamat,edEmail;
  TextInputLayout edNamaErr,edAlamatErr,edMailErr;
  Button btSave;
  ProgressBar pb;
  FirebaseFirestore fStore;
  FirebaseAuth fAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profil);

    edNama = findViewById(R.id.txEdNama);
    edAlamat = findViewById(R.id.txEdAlamat);
    edNamaErr = findViewById(R.id.txEdNamaErr);
    edAlamatErr = findViewById(R.id.txEdAlamatErr);
    edEmail = findViewById(R.id.txEdEmail);
    edMailErr = findViewById(R.id.txEdEmailErr);
    btSave = findViewById(R.id.btnEdSimpan);
    pb = findViewById(R.id.pbEditProfil);
    fStore = FirebaseFirestore.getInstance(); // Get Firebase Firestore Database
    fAuth = FirebaseAuth.getInstance(); // Get Firebase Auth
    String uid = fAuth.getCurrentUser().getUid(); // Get ID dari user yang login
    DocumentReference dref = fStore.collection("akun").document(uid);

    // Mengambil data dari database
    dref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()){
          DocumentSnapshot ds = task.getResult();
          if (ds.exists()){ // Jika data tersedia
            edNama.setText(ds.getString("nama"));
            edAlamat.setText(ds.getString("alamat"));
            edEmail.setText(ds.getString("email"));
          }
        }
      }
    });

    // Kondisi jika tombol save diklik
    btSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nm = edNama.getText().toString();
        almt = edAlamat.getText().toString();
        mail = edEmail.getText().toString();
        pb.setVisibility(View.VISIBLE);
        if (isValid()){
          simpanData();
          startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
      }
    });

  }

  // Method simpan data
  private void simpanData() {
    String uid = FirebaseAuth.getInstance().getUid();
    DocumentReference dr = fStore.collection("akun").document(uid);
    Map<String,Object> updateAkun = new HashMap<>();
    // Mengisi data dari string yang didapat
    updateAkun.put("nama",nm);
    updateAkun.put("alamat",almt);
    updateAkun.put("email",mail);
    // Memasukkan data Map kedalam database
    dr.set(updateAkun).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void unused) { // Jika sukses
        Log.d(TAG,"Akun telah diupdate! \n Nama : "+nm+"\nAlamat : "+almt+"\nEmail : "+mail);
        Toast.makeText(getApplicationContext(),"Data Telah Diubah!",Toast.LENGTH_SHORT).show();
      }
    }).addOnFailureListener(new OnFailureListener() { // Jika gagal
      @Override
      public void onFailure(@NonNull Exception e) {
        Log.d(TAG,"Kesalahan : "+e.toString());
        Toast.makeText(getApplicationContext(),"Data Gagal Diubah!",Toast.LENGTH_SHORT).show();
      }
    });
    pb.setVisibility(View.GONE);
  }

  // Method validasi input user
  protected boolean isValid(){
    nm = edNama.getText().toString();
    almt = edAlamat.getText().toString();
    if (nm.isEmpty()){
      edNamaErr.setError("Nama Tidak Boleh Kosong!");
      return false;
    }
    if (almt.isEmpty()){
      edAlamatErr.setError("Alamat Wajib Diisi!");
      return false;
    }
    return true;
  }
}