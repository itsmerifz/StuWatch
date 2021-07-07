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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DaftarAkun extends AppCompatActivity {
  // Deklarasi Variabel
  public static final String TAG = "TAG";
  EditText edEmail,edNama,edPass,edRPass,edAlamat;
  String nama,email,pass,rpass,uid,alamat;
  Button btnDaftar;
  TextView txGotoLogin;
  TextInputLayout mailErr,passErr,namaErr,rpassErr,alamatErr;
  ProgressBar progressBar;
  FirebaseAuth fAuth;
  FirebaseFirestore fStore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_daftar_akun); // Set layout activity

    //Deklarasi id layout dengan variabel
    edEmail = findViewById(R.id.txEmail);
    edNama = findViewById(R.id.txNama);
    edPass = findViewById(R.id.txPass);
    edRPass = findViewById(R.id.txRPass);
    edAlamat = findViewById(R.id.txAlamat);
    btnDaftar = findViewById(R.id.btnDaftar);
    txGotoLogin = findViewById(R.id.txLogin);
    mailErr = findViewById(R.id.txEmailErr);
    passErr = findViewById(R.id.txPassErr);
    namaErr = findViewById(R.id.txNamaErr);
    rpassErr = findViewById(R.id.txRPassErr);
    alamatErr = findViewById(R.id.txAlamatErr);
    progressBar = findViewById(R.id.progressBar);

    fAuth =FirebaseAuth.getInstance(); // Set Firebase Authentication
    fStore = FirebaseFirestore.getInstance(); // Set Firebase Firestore Database

    // Jika button daftar diklik
    btnDaftar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Get value dari input user
        email = edEmail.getText().toString().trim();
        pass = edPass.getText().toString().trim();
        nama = edNama.getText().toString();
        alamat = edAlamat.getText().toString();
        if (isValidate()){
          progressBar.setVisibility(View.VISIBLE); // Set Visibilitas Progress Bar

          // Register user ke firebase
          fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){
                Toast.makeText(getApplicationContext(),"Akun Sukses Dibuat!",Toast.LENGTH_LONG).show();
                submitData();
                startActivity(new Intent(getApplicationContext(),LoginAkun.class));
              }else{
                Toast.makeText(getApplicationContext(),"Akun Gagal Dibuat! \n Kesalahan: "
                        +task.getException().getMessage(),Toast.LENGTH_LONG).show();
              }
              progressBar.setVisibility(View.GONE);
            }
          });
        }
      }
    });

    // Jika textview login diklik
    txGotoLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),LoginAkun.class));
        finish();
      }
    });
  }

  // Method submit data
  private void submitData() {
    uid = fAuth.getCurrentUser().getUid(); // Get ID dari user terdaftar
    DocumentReference documentReference = fStore.collection("akun").document(uid);
    Map<String,Object> akun = new HashMap<>();
    akun.put("nama",nama);
    akun.put("email",email);
    akun.put("alamat",alamat);
    // Tambahkan data dari Map ke dalam database
    documentReference.set(akun).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void unused) {
        Log.d(TAG, "Akun telah ditambah! \n UserID : "+uid);
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Log.d(TAG,"Kesalahan : "+e.toString());
      }
    });
  }

  // Method validasi input
  protected boolean isValidate(){
    email = edEmail.getText().toString().trim();
    nama = edNama.getText().toString().trim();
    pass = edPass.getText().toString().trim();
    rpass = edRPass.getText().toString().trim();
    alamat = edAlamat.getText().toString().trim();

    if(email.isEmpty()){
      mailErr.setError("Email harus diisi!");
      return false;
    } else if (pass.isEmpty()){
      passErr.setError("Password harus diisi!");
      return false;
    } else if (nama.isEmpty()){
      namaErr.setError("Nama harus diisi");
      return false;
    } else if (!pass.equals(rpass)){
      passErr.setError("Password tidak cocok!");
      rpassErr.setError("Password tidak cocok!");
      return false;
    } else if (pass.length() < 6){
      passErr.setError("Password minimal 6 karakter!");
      return false;
    } else if (alamat.isEmpty()){
      alamatErr.setError("Alamat wajib diisi!");
    }
    return true;
  }

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseUser user = fAuth.getCurrentUser();
    // Cek kondisi jika user sudah terlogin maka langsung terarah ke MainActivity
    if (user != null){
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
  }
}

