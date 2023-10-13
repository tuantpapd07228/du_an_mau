package com.example.duanmauu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duanmauu.databinding.ActivityRegBinding;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.data.WriteData;

public class Reg_Activity extends AppCompatActivity {
    ReadData readData;
    ActivityRegBinding binding;
    WriteData writeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        readData = new ReadData(this);
        binding.btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.username.getText().length() < 5){
                    binding.username.setError("Chưa đủ ký tự!");
                }else if (binding.hoten.getText().length() < 10){
                    binding.hoten.setError("Chưa đủ ký tự!");
                }else if (binding.password.getText().length() < 5){
                    binding.password.setError("Chưa đủ ký tự!");
                }else if(binding.cfpassword.getText().length() < 5){
                    binding.cfpassword.setError("Chưa đủ ký tự!");
                }else if(binding.phone.getText().length() < 10){
                    binding.phone.setError("Chưa đủ ký tự!");
                } else if (binding.diachi.getText().length() < 10){
                    binding.diachi.setError("Chưa đủ ký tự!");
                }else if(binding.email.getText().length() < 10){
                    binding.email.setError("Chưa đủ ký tự!");
                }  else{
                    writeData = new WriteData(getApplicationContext());
                    writeData.insertNguoiDung(binding.username.getText().toString().trim()
                            ,binding.password.getText().toString().trim()
                            ,binding.hoten.getText().toString().trim()
                            ,binding.phone.getText().toString().trim(),
                            binding.diachi.getText().toString().trim(),
                    binding.email.getText().toString().trim());
                    Toast.makeText(Reg_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Reg_Activity.this, Login_Activity.class));
                }
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}