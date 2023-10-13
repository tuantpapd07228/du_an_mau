package com.example.duanmauu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.duanmauu.databinding.ActivitySachTheoTheLoaiBinding;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.adapter.SachTheoTheLoaiAdapTer;
import com.example.duanmauu.fragment.GioHangFragment;
import com.example.duanmauu.fragment.SachFragment;
import com.example.duanmauu.fragment.TheLoaiSach_Fragment;

import java.util.ArrayList;

public class SachTheoTheLoaiActivity extends AppCompatActivity {

    ActivitySachTheoTheLoaiBinding binding;
    SachTheoTheLoaiAdapTer adapTer;
    ReadData readData;
    ArrayList<Sach> arr;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySachTheoTheLoaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setData();
    }
    private void setData(){
        try {
            readData = new ReadData(getApplicationContext());
            arr = new ArrayList<>();
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            NguoiDung nguoiDung = (NguoiDung) b.getSerializable("nguoidung");
            String tentheloai = b.getString("tentheloai");
            gridView = findViewById(R.id.gridview);
            adapTer = new SachTheoTheLoaiAdapTer(this, arr, new SachTheoTheLoaiAdapTer.XuLiKhiNhanVaoSach() {
                @Override
                public Sach xulikhinhanvaosach(Sach sach) {
                    Intent intent1 = new Intent(SachTheoTheLoaiActivity.this, Shopping_Activity.class);
                    intent1.putExtra("sach", sach);
                    intent1.putExtra("nguoidung", nguoiDung);
                    startActivity(intent1);
                    return null;
                }
            });
            gridView.setAdapter(adapTer);

            readData.getTheLoaiSachTheoTungChuDe(tentheloai, new ReadData.XuliSachTheoTheLoai() {
                @Override
                public void xulisachtheotheloai(ArrayList<Sach> arr1) {
                    arr.addAll(arr1);
                    adapTer.notifyDataSetChanged();

                }
            });

        }catch (Exception e){
            System.out.println("loi dong 71 sachtheotheloaiactivity"+ e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(SachTheoTheLoaiActivity.this);
        menuInflater.inflate(R.menu.menu_cart_sach_theloai, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()) {
            case R.id.menu_sach_toolbar:
                fragment = new SachFragment();
                break;
            case R.id.menu_theloai_toolbar:
                fragment = new TheLoaiSach_Fragment();
                break;
            case R.id.menu_giohang_toolbar:
                fragment = new GioHangFragment();
                break;
        }
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.framelayout, fragment);
            fragmentTransaction.commit();
        }catch (Exception e){}
        return super.onOptionsItemSelected(item);
    }
}