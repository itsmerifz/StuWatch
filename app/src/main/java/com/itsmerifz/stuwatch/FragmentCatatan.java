package com.itsmerifz.stuwatch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentCatatan extends Fragment {
  // Deklarasi variabel
  TextView namas;
  EditText txCatat;
  DatabaseReference db;
  String kodee = MenuInputData.kode;
  public FragmentCatatan() { // Konstruktor FragmentCatatan
  }
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate layout kedalam fragment
    View v = inflater.inflate(R.layout.fragment_catatan, container, false);
    namas = v.findViewById(R.id.tv_namaS);
    txCatat = v.findViewById(R.id.txCatatan);
    FloatingActionButton fab = v.findViewById(R.id.floatingActionButton3);
    Bundle b = getActivity().getIntent().getExtras(); // Get bundle dari intent
    namas.setText("Nama     :   "+b.getString("nama"));

    db = FirebaseDatabase.getInstance().getReference(); // Get Firebase Realtime Database
    // Get data dari database
    db.child("siswa").child(kodee).child("nilai").child("catatan").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){
          Nilai n =  ds.getValue(Nilai.class);
          txCatat.setText(n.getCatatan());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    // Kondisi jika floating action button diklik
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String tx = txCatat.getText().toString();
        inputCatatan(new Nilai(tx));
      }
    });
    return v;
  }

  // Method input catatan
  private void inputCatatan(Nilai n) {
    db.child("siswa").child(kodee).child("nilai").child("catatan").removeValue(); // Menghapus data sebelumnya
    // Memasukkan data baru
    db.child("siswa").child(kodee).child("nilai").child("catatan").push().setValue(n).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void unused) {
        Toast.makeText(getContext(),"Catatan berhasil diinput!",Toast.LENGTH_LONG).show();
      }
    });
  }
}