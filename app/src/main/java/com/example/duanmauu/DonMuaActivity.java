package com.example.duanmauu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.duanmauu.adapter.ViewPageFragment;
import com.example.duanmauu.databinding.ActivityDonMuaBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DonMuaActivity extends AppCompatActivity {
    ActivityDonMuaBinding binding;
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonMuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX() <e1.getX()){
                    finish();
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });



        ViewPageFragment viewPageFragment = new ViewPageFragment(this);
        binding.donmuaviewpager2.setAdapter(viewPageFragment);

        new TabLayoutMediator(binding.donmuatablayout, binding.donmuaviewpager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Xác nhận");
                        break;
                    case 1:
                        tab.setText("Vận chuyển");
                        break;
                    case 2:
                        tab.setText("Hoàn thành");
                        break;
                    case 3:
                        tab.setText("Đã hủy");
                        break;
                }
            }
        }).attach();


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public com.example.duanmauu.model.NguoiDung getNguoidung(){
        Intent intent = getIntent();
        com.example.duanmauu.model.NguoiDung nguoiDung = (com.example.duanmauu.model.NguoiDung) intent.getSerializableExtra("nguoidung");
        return nguoiDung;
    }
}