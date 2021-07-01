package com.itsmerifz.stuwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MenuHasilSiswa extends AppCompatActivity {
  TabLayout tl;
  ViewPager2 vp;
  AdapterFragmentHasil adapter;
  public static String kode,nama;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_hasil_siswa);
    tl = findViewById(R.id.tabMenuInput2);
    vp = findViewById(R.id.viewPager2);
    FragmentManager fm = getSupportFragmentManager();
    adapter = new AdapterFragmentHasil(fm,getLifecycle());
    vp.setAdapter(adapter);
    Bundle b = getIntent().getExtras();

    kode = b.getString("kd");
    nama = b.getString("nama");

    tl.addTab(tl.newTab().setText(R.string.tab_text_1));
    tl.addTab(tl.newTab().setText(R.string.tab_text_3));

    tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        tl.selectTab(tl.getTabAt(position));
      }
    });
  }
}