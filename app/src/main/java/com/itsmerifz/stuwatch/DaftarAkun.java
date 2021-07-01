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
  public static final String TAG = "TAG";
  EditText edEmail,edNama,edPass,edRPass;
  String nama,email,pass,rpass,uid;
  Button btnDaftar;
  TextView txGotoLogin;
  TextInputLayout mailErr,passErr,namaErr,rpassErr;
  ProgressBar progressBar;
  FirebaseAuth fAuth;
  FirebaseFirestore fStore;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_daftar_akun);

    edEmail = findViewById(R.id.txEmail);
    edNama = findViewById(R.id.txNama);
    edPass = findViewById(R.id.txPass);
    edRPass = findViewById(R.id.txRPass);
    btnDaftar = findViewById(R.id.btnDaftar);
    txGotoLogin = findViewById(R.id.txLogin);
    mailErr = findViewById(R.id.txEmailErr);
    passErr = findViewById(R.id.txPassErr);
    namaErr = findViewById(R.id.txNamaErr);
    rpassErr = findViewById(R.id.txRPassErr);
    progressBar = findViewById(R.id.progressBar);

    fAuth =FirebaseAuth.getInstance();
    fStore = FirebaseFirestore.getInstance();

    btnDaftar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        email = edEmail.getText().toString().trim();
        pass = edPass.getText().toString().trim();
        nama = edNama.getText().toString().trim();
        if (isValidate()){
          progressBar.setVisibility(View.VISIBLE); // Set Visibilitas Progress Bar

          //Register user ke firebase
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

    txGotoLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(),LoginAkun.class));
        finish();
      }
    });
  }

  private void submitData() {
    uid = fAuth.getCurrentUser().getUid();
    DocumentReference documentReference = fStore.collection("akun").document(uid);
    Map<String,Object> akun = new HashMap<>();
    akun.put("nama",nama);
    akun.put("email",email);
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

  protected boolean isValidate(){
    email = edEmail.getText().toString().trim();
    nama = edNama.getText().toString().trim();
    pass = edPass.getText().toString().trim();
    rpass = edRPass.getText().toString().trim();

    if(email.isEmpty()){
      mailErr.setError("Email harus diisi!");
      return false;
    }

    if (pass.isEmpty()){
      passErr.setError("Password harus diisi!");
      return false;
    }

    if (nama.isEmpty()){
      namaErr.setError("Nama harus diisi");
      return false;
    }

    if (!pass.equals(rpass)){
      passErr.setError("Password tidak cocok!");
      rpassErr.setError("Password tidak cocok!");
      return false;
    }

    if (pass.length() < 6){
      passErr.setError("Password minimal 6 karakter!");
      return false;
    }
    return true;
  }

  @Override
  protected void onStart() {
    super.onStart();
    FirebaseUser user = fAuth.getCurrentUser();
    if (user != null){
      startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
  }
}

