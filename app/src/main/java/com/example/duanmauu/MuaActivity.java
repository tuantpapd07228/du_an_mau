package com.example.duanmauu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duanmauu.adapter.DatSachAdapter;
import com.example.duanmauu.databinding.ActivityMuaBinding;
import com.example.duanmauu.data.GetIP;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.data.WriteData;
import com.example.duanmauu.model.GioHang;
import com.example.duanmauu.model.itf.ITFGioHang;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.model.Sach;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MuaActivity extends AppCompatActivity {
    ActivityMuaBinding binding;
    NguoiDung nguoiDung;
    Sach sach;
    int soluong =1;
    String url = GetIP.IP+":8686/duanmau/update_nguoidung.php";
    NguoiDung nguoiDungact;
    WriteData writeData;

    ReadData readData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        readData = new ReadData(MuaActivity.this);

        writeData = new WriteData(MuaActivity.this);
        nguoiDungact = new NguoiDung();
        Intent intent = getIntent();
        sach = (Sach) intent.getSerializableExtra("sach");
        nguoiDung = (NguoiDung) intent.getSerializableExtra("nguoidung");
        setData();
        binding.soluong.setText(soluong+"");
        binding.tongtien.setText(getTongtienhang()+"");
        binding.tongcong.setText((getTongtienhang()+30000)+"");
        binding.cong1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong+=1;
                if (sach.getSoLuong() < soluong){
                    soluong = sach.getSoLuong();
                }
                binding.soluong.setText(soluong+"");
                binding.tongtien.setText(getTongtienhang()+"");
                binding.tongcong.setText((getTongtienhang()+30000)+"");

            }
        });
        binding.tru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(binding.soluong.getText().toString()) == 1){
                    soluong = 2;
                }
                    binding.tru1.setEnabled(true);
                    soluong -=1;
                    binding.soluong.setText(soluong+"");
                    binding.tongtien.setText(getTongtienhang()+"");
                    binding.tongcong.setText((getTongtienhang()+30000)+"");

            }
        });

        binding.themvaogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeData.InsertGioHang(nguoiDung.getId(), sach.getIdSach(), binding.soluong.getText().toString());
            }
        });
        binding.thongtinnguoimua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Dialog dialog = new Dialog(MuaActivity.this);
                    dialog.setContentView(R.layout.update_info);
                    EditText name = dialog.findViewById(R.id.name2);
                    EditText email = dialog.findViewById(R.id.email2);
                    EditText addrr = dialog.findViewById(R.id.adrress2);
                    EditText phone = dialog.findViewById(R.id.phone2);
                    Button btnud = dialog.findViewById(R.id.btnupdateinfo);
                    name.setText(nguoiDung.getHoten());
                    email.setText(nguoiDung.getEmail());
                    addrr.setText(nguoiDung.getDiachi());
                    phone.setText(nguoiDung.getPhone());

                    btnud.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (name.getText().toString().isEmpty() || email.getText().toString().isEmpty() || addrr.getText().toString().isEmpty()|| phone.getText().toString().isEmpty()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MuaActivity.this);
                                builder.setTitle("Vui lòng xem lại thông tin đã nhập!");
                                builder.setPositiveButton("OK", null);
                                builder.show();
                            }else {
                                RequestQueue requestQueue = Volley.newRequestQueue(MuaActivity.this);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (requestQueue != null) {
                                            Toast.makeText(MuaActivity.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                                            nguoiDung.setHoten(name.getText().toString().trim());
                                            nguoiDung.setEmail(email.getText().toString().trim());
                                            nguoiDung.setDiachi(addrr.getText().toString().trim());
                                            nguoiDung.setPhone(phone.getText().toString().trim());
                                            setData();
                                            dialog.dismiss();
                                        }
                                    }
                                },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("idnguoidung", nguoiDung.getId());
                                        map.put("hoten", name.getText().toString().trim());
                                        map.put("diachi", addrr.getText().toString().trim());
                                        map.put("email", email.getText().toString().trim());
                                        map.put("sdt", phone.getText().toString().trim());
                                        return map;
                                    }
                                };
                                requestQueue.add(stringRequest);
                            }
                        }
                    });
                    dialog.show();
                }catch (Exception e){
                    Log.e("EEEEEEEEEEEEEEEE", e.getMessage());
                }

            }
        });

    }


    public void setData() {
        int id = getApplicationContext().getResources().getIdentifier("drawable/"+sach.getHinh(), null, getPackageName());
        binding.sach.setImageResource(id);
        binding.giasach.setText(sach.getGiaBan()+"");
        binding.diachi.setText(nguoiDung.getDiachi());
        binding.ten.setText(nguoiDung.getHoten());
        binding.tensach.setText(sach.getTieuDe());
        binding.mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idhoadon1 = getIdHoaDon();
                Calendar calendar = new GregorianCalendar();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);
                writeData.insertHoaDon(idhoadon1, nguoiDung.getId(), year + "/" + (1+month) + "/" + day, new WriteData.XuLiHoaDon() {
                    @Override
                    public void xulihoadon(String idhoandon) {
                        writeData.inserDonHangChiTiet(idhoadon1, sach.getIdSach(), Integer.parseInt(binding.soluong.getText().toString()));
                        Toast.makeText(MuaActivity.this, "Đơn hàng đang được xác nhận", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private String getIdHoaDon(){
        Random random = new Random();
        int iddonhang = random.nextInt(999999999)+1;
        return iddonhang+"";
    }

    private int getTongtienhang() {
        int tongtien =0;
        tongtien = soluong * (sach.getGiaBan());
        return tongtien;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_cart_sach_theloai, menu);
        return super.onCreateOptionsMenu(menu);
    }
}