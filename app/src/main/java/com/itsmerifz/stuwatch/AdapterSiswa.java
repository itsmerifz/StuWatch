package com.itsmerifz.stuwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSiswa extends BaseAdapter {
  // Deklarasi Variabel
  private final ArrayList<Siswa> arrSiswa;
  LayoutInflater inflater;
  Context c;

  public AdapterSiswa(Context ctx) { // Konstruktor AdapterSiswa
    c = ctx;
    inflater = LayoutInflater.from(c);
    arrSiswa = new ArrayList<Siswa>();
    arrSiswa.addAll(DaftarSiswa.siswaArrayList);
  }

  public class ViewHolder{ // Set kelas ViewHolder
    TextView nama,nis;
  }

  @Override
  public int getCount() {
    return DaftarSiswa.siswaArrayList.size();
  }

  @Override
  public Object getItem(int p) {
    return DaftarSiswa.siswaArrayList.get(p);
  }

  @Override
  public long getItemId(int p) {
    return p;
  }

  @Override
  // Set tampilan dari adapter
  public View getView(int p, View v, ViewGroup vg) {
    final ViewHolder holder;
    if (v == null){
      holder = new ViewHolder();
      v = inflater.inflate(R.layout.list_siswa,null);
      holder.nama = v.findViewById(R.id.tvNama_Siswa);
      holder.nis = v.findViewById(R.id.tvNis_Siswa);
      v.setTag(holder);
    }else{
      holder = (ViewHolder) v.getTag();
    }
    // Set text dari array list yang diambil
    holder.nama.setText(DaftarSiswa.siswaArrayList.get(p).getNama());
    holder.nis.setText(DaftarSiswa.siswaArrayList.get(p).getNis());
    return v;
  }

  @Override
  public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
  }
}
