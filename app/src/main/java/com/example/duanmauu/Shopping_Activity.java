package com.example.duanmauu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.example.duanmauu.databinding.ActivityShoppingBinding;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.data.WriteData;

public class Shopping_Activity extends AppCompatActivity {
    ActivityShoppingBinding binding;
    NguoiDung nguoiDung;
    WriteData writeData;

    Sach sach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setData();

    }

    private void setData(){
        writeData = new WriteData(Shopping_Activity.this);
        Intent intent = getIntent();
        sach = (Sach) intent.getSerializableExtra("sach");
        nguoiDung = (NguoiDung) intent.getSerializableExtra("nguoidung");
        Toast.makeText(this, "ten sach"+sach.getTieuDe(), Toast.LENGTH_SHORT).show();
        try {
            int id = getApplication().getResources().getIdentifier("drawable/"+sach.getHinh(), null, getApplication().getPackageName());
            binding.giaban.setText(sach.getGiaBan()+"");
            binding.sach.setImageResource(id);
            binding.mota.setText(sach.getMota());
            binding.tensach.setText(sach.getTieuDe());
            binding.tacgiashopping.setText(sach.getTenTheLoai());
            binding.themvaogio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("shopping nguoidung "+ nguoiDung.getHoten());
                    writeData.InsertGioHang(nguoiDung.getId(), sach.getIdSach(), 1+"");
                }
            });
            binding.mua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = new Intent(Shopping_Activity.this, MuaActivity.class);
                    intent1.putExtra("sach", sach);
                    intent1.putExtra("nguoidung",nguoiDung);
                    startActivity(intent1);
                }
            });
        }catch (Exception e){
            Log.e("EEEEEEEEEEEEEEEEE",e.getMessage());
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_cart_sach_theloai, menu);
        return super.onCreateOptionsMenu(menu);
    }
}