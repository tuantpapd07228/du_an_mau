package com.example.duanmauu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duanmauu.databinding.ActivityLoginBinding;
import com.example.duanmauu.data.GetIP;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.itf.SelectNguoidung;

import java.util.ArrayList;

public class Login_Activity extends AppCompatActivity {
    private ReadData readData;
    private ActivityLoginBinding binding;
    public static ArrayList<NguoiDung> arr;
    private String url, uername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        readData = new ReadData(getApplicationContext());
        url = GetIP.ip+ ":8686/duanmau/get_nguoidung.php";
        uername = binding.username.getText().toString().trim();
        arr = new ArrayList<>();

        setUS();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUS();
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_Activity.this, Reg_Activity.class));
            }
        });

    }

    private void checkUS(){
        readData.getNguoiDung1(binding.username.getText().toString(), new SelectNguoidung() {
            @Override
            public void selectNguoidung(NguoiDung nguoiDung) {
                String us = binding.username.getText().toString().trim();
                String pw = binding.password.getText().toString().trim();
                if (us.equals(nguoiDung.getUserName()) && pw.equalsIgnoreCase(nguoiDung.getPassWord())) {
                    Toast.makeText(Login_Activity.this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                    checkRember();
                    Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                    intent.putExtra("nguoidung", nguoiDung);
                    startActivity(intent);
                }
            }
        });
    }

    private void checkRember() {
        if (binding.chkghinho.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences("check", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("chk", true);
            editor.putString("us", binding.username.getText().toString().trim());
            editor.putString("pw", binding.password.getText().toString().trim());
            editor.commit();
        }
        return;
    }

    private void setUS(){
        SharedPreferences sharedPreferences = getSharedPreferences("check", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("chk", false)){
            binding.chkghinho.setChecked(true);
            binding.username.setText(sharedPreferences.getString("us", null));
            binding.password.setText(sharedPreferences.getString("pw", null));
        }
        return;
    }


}