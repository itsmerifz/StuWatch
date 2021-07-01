package com.itsmerifz.stuwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MenuInputData extends AppCompatActivity {
  TabLayout tl;
  ViewPager2 vp2;
  AdapterFragmentInput adapter;
  public static String kode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_input_data);

    tl = findViewById(R.id.tabMenuInput);
    vp2 = findViewById(R.id.viewPager);
    FragmentManager fm = getSupportFragmentManager();
    adapter = new AdapterFragmentInput(fm,getLifecycle());
    vp2.setAdapter(adapter);
    Bundle b = getIntent().getExtras();
    kode = b.getString("kd");

    tl.addTab(tl.newTab().setText(R.string.tab_text_1));
    tl.addTab(tl.newTab().setText(R.string.tab_text_3));

    tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        vp2.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
      @Override
      public void onPageSelected(int position) {
        tl.selectTab(tl.getTabAt(position));
      }
    });
  }
}