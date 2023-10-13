package com.example.duanmauu.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duanmauu.fragment.HoaDonFragment;
import com.example.duanmauu.fragment.HoanThanhFragment;
import com.example.duanmauu.fragment.HuyFragment;

public class ViewPageFragment extends FragmentStateAdapter {
    public ViewPageFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new HoaDonFragment();
        if (position == 1) return new HoanThanhFragment();
        if (position == 2) return new HuyFragment();
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
