package com.itsmerifz.stuwatch;

import java.io.Serializable;

public class Akun implements Serializable {
  private String email,nama;

  public Akun(){}

  public String getNama() {
    return nama;
  }

  public void setNama(String nm) {
    this.nama = nm;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String em) {
    this.email = em;
  }

  @Override
  public String toString() {
    return "Akun{" +
            "email='" + email + '\'' +
            ", nama='" + nama + '\'' +
            '}';
  }

  public Akun(String mail,String name){
    nama = name;
    email = mail;
  }
}
