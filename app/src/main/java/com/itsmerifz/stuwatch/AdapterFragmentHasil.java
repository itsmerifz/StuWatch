package com.itsmerifz.stuwatch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterFragmentHasil extends FragmentStateAdapter {
  public AdapterFragmentHasil(@NonNull FragmentManager fragment,@NonNull Lifecycle lc) {
    super(fragment,lc);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    switch (position){
      case 1:
        return new FragmentHasilCatatan();
    }
    return new FragmentHasilPengetahuan();
  }

  @Override
  public int getItemCount() {
    return 2;
  }
}
