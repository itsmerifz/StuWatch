package com.itsmerifz.stuwatch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentPengetahuan extends Fragment {
  public static final String TAG = "TAG";
  EditText ipa,ips,agama,senbud,penjas,pkn,inggris,indo;
  int nIpa,nIps,nAgama,nSenbud,nPenjas,nPkn,nInggris,nIndo,nRata;
  String kodee;
  DatabaseReference db;
  FloatingActionButton fab;
  TextView tv;

  public FragmentPengetahuan() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_pengetahuan, container, false);
    ipa = v.findViewById(R.id.txIPA);
    ips = v.findViewById(R.id.txIPS);
    agama = v.findViewById(R.id.txAgama);
    senbud= v.findViewById(R.id.txSenbud);
    penjas= v.findViewById(R.id.txPenjas);
    pkn = v.findViewById(R.id.txPkn);
    inggris = v.findViewById(R.id.txInggris);
    indo = v.findViewById(R.id.txIndo);
    tv = v.findViewById(R.id.tv_namaS);
    fab = v.findViewById(R.id.floatingActionButton);
    db = FirebaseDatabase.getInstance().getReference();
    Bundle b = getActivity().getIntent().getExtras();
    tv.setText("Nama     :   "+b.getString("nama"));
    kodee = MenuInputData.kode;

    db.child("siswa").child(kodee).child("nilai").child("pengetahuan").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot ds : snapshot.getChildren()){
          Nilai n = ds.getValue(Nilai.class);
          ipa.setText(n.getIpa());
          ips.setText(n.getIps());
          agama.setText(n.getAgama());
          pkn.setText(n.getPkn());
          inggris.setText(n.getInggris());
          indo.setText(n.getIndo());
          senbud.setText(n.getSenbud());
          penjas.setText(n.getPenjas());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        nIpa = Integer.parseInt(ipa.getText().toString());
        nIps = Integer.parseInt(ips.getText().toString());
        nAgama = Integer.parseInt(agama.getText().toString());
        nIndo = Integer.parseInt(indo.getText().toString());
        nInggris = Integer.parseInt(inggris.getText().toString());
        nPenjas = Integer.parseInt(penjas.getText().toString());
        nSenbud = Integer.parseInt(senbud.getText().toString());
        nPkn = Integer.parseInt(pkn.getText().toString());

        nRata = (nAgama+nPkn+nSenbud+nPenjas+nInggris+nIndo+nIps+nIpa) / 8;
        inputNilai(new Nilai(String.valueOf(nIpa),String.valueOf(nIps),String.valueOf(nPkn),String.valueOf(nSenbud),String.valueOf(nPenjas),String.valueOf(nIndo),String.valueOf(nInggris),String.valueOf(nAgama),String.valueOf(nRata)));
      }
    });

    return v;
  }

  private void inputNilai(Nilai n){
    db.child("siswa").child(kodee).child("nilai").child("pengetahuan").removeValue();
    db.child("siswa").child(kodee).child("nilai").child("pengetahuan").push().setValue(n).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void unused) {
        Toast.makeText(getContext(),"Nilai berhasil diinput",Toast.LENGTH_LONG).show();
        Log.i(TAG,"Nilai sukses diinput!");
      }
    });
  }
}