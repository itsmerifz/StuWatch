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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAkun extends AppCompatActivity {
  // Deklarasi Variabel
  public static final String TAG = "TAG";
  TextInputLayout mailErr,passErr;
  EditText email,pass;
  Button btLogin;
  TextView txDaftar;
  String em,pw;
  ProgressBar progressBar;
  FirebaseAuth fAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_akun);

    mailErr = findViewById(R.id.txEmailErr);
    passErr = findViewById(R.id.txPassErr);
    email = findViewById(R.id.txEmail);
    pass = findViewById(R.id.txPass);
    btLogin = findViewById(R.id.btnLogin);
    progressBar = findViewById(R.id.pbLogin);
    txDaftar = findViewById(R.id.txDaftar);
    fAuth = FirebaseAuth.getInstance(); // Get Firebase Auth

    // Kondisi ketika textview diklik
    txDaftar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(LoginAkun.this,DaftarAkun.class));
      }
    });

    // Kondisi ketika tombol login diklik
    btLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        em = email.getText().toString().trim();
        pw = pass.getText().toString().trim();
        if (isValid()){
          progressBar.setVisibility(View.VISIBLE);
          // Aksi login kedalam firebase
          fAuth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){ // Jika berhasil
                Log.d(TAG,"Login Sukses!");
                FirebaseUser user = fAuth.getCurrentUser(); // Mengambil data user login
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
              }else{ // Jika gagal
                Log.w(TAG,"Login Gagal! \n Kesalahan : "+task.getException());
                Toast.makeText(getApplicationContext(),"Login Gagal!",Toast.LENGTH_LONG).show();
              }
              progressBar.setVisibility(View.GONE);
            }
          });
        }
      }
    });
  }

  // Method validasi input user
  private boolean isValid(){
    em = email.getText().toString().trim();
    pw = pass.getText().toString().trim();
    if (em.isEmpty() && pw.isEmpty()){
      mailErr.setError("Email harus diisi!");
      passErr.setError("Password harus diisi!");
      return false;
    } else if (pw.isEmpty()){
      passErr.setError("Password harus diisi!");
      return false;
    } else if (pw.length() < 6){
      passErr.setError("Password minimal berisi 6 karakter");
      return false;
    } else if (em.isEmpty()){
      mailErr.setError("Email harus diisi!");
      return false;
    }
    return true;
  }

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseUser user = fAuth.getCurrentUser(); // Mengambil data user
    // Cek kondisi jika user sudah login
    if (user != null){
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
  }

  @Override
  // Aksi back button
  public void onBackPressed() {
    // Akhiri aplikasi
    finishAffinity();
    finish();
  }
}
