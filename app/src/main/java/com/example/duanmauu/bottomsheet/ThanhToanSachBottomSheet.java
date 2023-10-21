package com.example.duanmauu.bottomsheet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duanmauu.MuaActivity;
import com.example.duanmauu.R;
import com.example.duanmauu.adapter.DatSachAdapter;
import com.example.duanmauu.data.GetIP;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.data.WriteData;
import com.example.duanmauu.model.GioHang;
import com.example.duanmauu.model.NguoiDung;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ThanhToanSachBottomSheet extends BottomSheetDialogFragment {
    String url = GetIP.IP +":8686/duanmau/update_nguoidung.php";

    TextView txthoten, txtdiachi, txttienhang, txttongtienhang;
    Button btnxacnhan;
    RecyclerView recyclerView;
    DatSachAdapter datSachAdapter;
    ArrayList<GioHang> gioHangArrayList;
    NguoiDung nguoiDung;
    MuaActivity activity ;
    LinearLayout linearLayout;

    WriteData writeData;
    ReadData readData;

    GioHang gioHang;
    int iddonhang;
//    public ThanhToanSachBottomSheet(GioHang gioHang) {
//        this.gioHang = gioHang;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.thanh_toan_sach_bottom_sheet, container, false);
//        txthoten = v.findViewById(R.id.thanhtoan_hoten);
//        txtdiachi = v.findViewById(R.id.thanhtoan_diachi);
//        txttienhang = v.findViewById(R.id.thanhtoan_tongtien);
//        txttongtienhang = v.findViewById(R.id.thanhtoan_tongtienhang);
//        btnxacnhan = v.findViewById(R.id.thanhtoan_xacnhan);
//        recyclerView = v.findViewById(R.id.thanhtoan_recycleview);
//        activity = (MuaActivity) getActivity();
//        readData = new ReadData(getContext());
//        writeData = new WriteData(getContext());
//        linearLayout = v.findViewById(R.id.thanhtoan_thongtinnguoimua);
////        nguoiDung = activity.
//        LinearLayoutManager l = new LinearLayoutManager(getContext());
//        l.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(l);
//        Random random = new Random();
//        iddonhang = random.nextInt(999999999)+1;
//        setdata();
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateInfoNguoiMua();
//            }
//        });
//        btnxacnhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                insertHoaDon(gioHang);
//            }
//        });
//
//
//
//

        return v;
    }
    private void setdata(){
        gioHangArrayList = new ArrayList<>();
        gioHangArrayList.add(gioHang);
        datSachAdapter = new DatSachAdapter(gioHangArrayList, getContext());
        recyclerView.setAdapter(datSachAdapter);
    }
    private void updateInfoNguoiMua(){
        try {
            Dialog dialog = new Dialog(getContext());
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Vui lòng xem lại thông tin đã nhập!");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }else {
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (requestQueue != null) {
                                    Toast.makeText(getContext(), "ban da update thanh cong", Toast.LENGTH_SHORT).show();
                                    nguoiDung.setHoten(name.getText().toString().trim());
                                    nguoiDung.setEmail(email.getText().toString().trim());
                                    nguoiDung.setDiachi(addrr.getText().toString().trim());
                                    nguoiDung.setPhone(phone.getText().toString().trim());
                                    txthoten.setText(name.getText().toString());
                                    txtdiachi.setText(addrr.getText().toString());
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
    private void insertHoaDon(GioHang gioHang){
        writeData.insertHoaDon(iddonhang+"", nguoiDung.getId(), getDate(), new WriteData.XuLiHoaDon() {
            @Override
            public void xulihoadon(String idhoandon) {
                writeData.inserDonHangChiTiet(idhoandon+"" , gioHang.getIdSach(), gioHang.getSoLuong());
                Toast.makeText(activity, "Xin cảm ơn", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }


    private String getDate(){
        Calendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return year + "/" + month + "/" + day;
//        writeData.insertHoaDon(idhoadon, nguoiDung.getId(), , new WriteData.XuLiHoaDon() {
//            @Override
//            public void xulihoadon(String idhoandon1) {
//                System.out.println("id hoa don 137 "+idhoadon);
//                for (int i = 0; i < arrtinhtien.size(); i++) {
//                    writeData.inserDonHangChiTie(idhoadon, arrtinhtien.get(i).getIdSach(), arrtinhtien.get(i).getSoLuongTrongGioHang());
//                    deleteData.deleteGioHang(nguoiDung.getId(), arrtinhtien.get(i).getIdSach());
//                }
//                arr.clear();
//                arrtinhtien.clear();
//                tongtien.setText(setTongTien(arrtinhtien)+"");
//                getData();
//                adapter.notifyDataSetChanged();
//
//            }
//        });
    }
}