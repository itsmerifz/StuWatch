package com.itsmerifz.stuwatch;

import java.io.Serializable;

public class Siswa implements Serializable {
  private String nama,nis,kelas,kode;
  public Siswa() {
  }

  public Siswa(String nm,String kls,String no){
    nama = nm;
    kelas = kls;
    nis = no;
  }

  public String getKode() {
    return kode;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getNis() {
    return nis;
  }

  public void setNis(String nis) {
    this.nis = nis;
  }

  public String getKelas() {
    return kelas;
  }

  public void setKelas(String kelas) {
    this.kelas = kelas;
  }

  @Override
  public String toString() {
    return "Siswa{" +
            "nama='" + nama + '\'' +
            ", nis='" + nis + '\'' +
            ", kelas='" + kelas + '\'' +
            ", kode='" + kode + '\'' +
            '}';
  }
}
