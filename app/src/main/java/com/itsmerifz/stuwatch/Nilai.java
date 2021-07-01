package com.itsmerifz.stuwatch;

import java.io.Serializable;

public class Nilai  implements Serializable {
  private String ipa,ips,agama,pkn,senbud,penjas,indo,inggris,rata;
  private String catatan,kode,kode2;

  public String getKode2() {
    return kode2;
  }

  public void setKode2(String kode2) {
    this.kode2 = kode2;
  }

  public String getKode() {
    return kode;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public String getCatatan() {
    return catatan;
  }

  public void setCatatan(String catatan) {
    this.catatan = catatan;
  }

  public String getIpa() {
    return ipa;
  }

  public void setIpa(String ipa) {
    this.ipa = ipa;
  }

  public String getIps() {
    return ips;
  }

  public void setIps(String ips) {
    this.ips = ips;
  }

  public String getAgama() {
    return agama;
  }

  public void setAgama(String agama) {
    this.agama = agama;
  }

  public String getPkn() {
    return pkn;
  }

  public void setPkn(String pkn) {
    this.pkn = pkn;
  }

  public String getSenbud() {
    return senbud;
  }

  public void setSenbud(String senbud) {
    this.senbud = senbud;
  }

  public String getPenjas() {
    return penjas;
  }

  public void setPenjas(String penjas) {
    this.penjas = penjas;
  }

  public String getIndo() {
    return indo;
  }

  public void setIndo(String indo) {
    this.indo = indo;
  }

  public String getInggris() {
    return inggris;
  }

  public void setInggris(String inggris) {
    this.inggris = inggris;
  }

  public String getRata() {
    return rata;
  }

  public void setRata(String rata) {
    this.rata = rata;
  }

  public Nilai(){}

  public Nilai(String ipaa,String ipss,String pknn, String senbudd,String penjass,String indoo,String inggriss,String agamaa, String rataa){
    ipa = ipaa;
    ips = ipss;
    pkn = pknn;
    senbud = senbudd;
    penjas = penjass;
    indo = indoo;
    inggris = inggriss;
    agama = agamaa;
    rata = rataa;
  }

  public Nilai(String catat){
    catatan = catat;
  }

  @Override
  public String toString() {
    return "Nilai{" +
            "ipa='" + ipa + '\'' +
            ", ips='" + ips + '\'' +
            ", agama='" + agama + '\'' +
            ", pkn='" + pkn + '\'' +
            ", senbud='" + senbud + '\'' +
            ", penjas='" + penjas + '\'' +
            ", indo='" + indo + '\'' +
            ", inggris='" + inggris + '\'' +
            ", rata='" + rata + '\'' +
            ", catatan='" + catatan + '\'' +
            ", kode='" + kode + '\'' +
            ", kode2='" + kode2 + '\'' +
            '}';
  }
}
