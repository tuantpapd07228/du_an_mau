package com.example.duanmauu.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class WriteData {
    Context context;
    String id,ten,mota,vitri;
    String urlnguoidung = GetIP.IP +":8686/duanmau/insert_nguoidung.php";
    String urlgiohang = GetIP.IP +":8686/duanmau/insert_giohang.php";
    String urlupdategiohang = GetIP.IP +":8686/duanmau/update_soluongtronggiohang.php";
    String urlhoadon = GetIP.IP +":8686/duanmau/insert_hoadon.php";
    String urlhoadonchitiet= GetIP.IP +":8686/duanmau/insert_hoadonchitiet.php";

    public WriteData(Context context) {
        this.context = context;
    }

    public void insertNguoiDung(String us, String pw, String name, String phone,String diachi,String email){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlnguoidung, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.length()>0){
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERORRRRRRR", error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("hoTen", name);
                map.put("userName",us);
                map.put("passWord", pw);
                map.put("SDT", phone);
                map.put("diaChi", diachi);
                map.put("email", email);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    // insert gio han

   public void InsertGioHang( String id, String idsach, String soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response+" write 67");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println( "write 72 "+error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("soluong", soluong);
                map.put("idnguoidung", id);
                map.put("idsach", idsach);
                return map;
            }
        };
        requestQueue.add(stringRequest);
   }

          // update gio hang

   public void UpdateGioHang(String idgiohang, int soLuong){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupdategiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("writeeeeeeeeeeeeeeeeeeeeeeeeeeeeee 98");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idgiohang", idgiohang);
                map.put("soluong", String.valueOf(soLuong));
                return map;
            }
        };
        requestQueue.add(stringRequest);
   }

// insert hoa don
    public interface XuLiHoaDon{void xulihoadon(String idhoandon);}
   public void insertHoaDon(String idhoadon, String idnguoimua, String ngaymua, XuLiHoaDon xuLiHoaDon){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadon, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                xuLiHoaDon.xulihoadon(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("loi 130 write"+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idhoadon", idhoadon);
                map.put("idnguoidung", idnguoimua);
                map.put("ngaymua", ngaymua);
                return map;
            }
        };
        requestQueue.add(stringRequest);
   }

   // insert don hang chi tiet

    public void inserDonHangChiTiet(String idhoadon, String idsach, int soluong){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadonchitiet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("cam on ban da dat hang 152 write");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("loi 152 write");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idhoadon", idhoadon);
                map.put("idsach", idsach);
                map.put("soluong", String.valueOf(soluong));
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
