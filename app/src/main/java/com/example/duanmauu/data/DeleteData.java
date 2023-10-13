package com.example.duanmauu.data;

import android.content.Context;

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

public class DeleteData {
    Context context;
    String urlgiohang= GetIP.ip+":8686/duanmau/delete_giohang.php";
    String urlcanceldonhang = GetIP.ip+":8686/duanmau/huydon.php";

    public DeleteData(Context context) {
        this.context = context;
    }

    public void deleteGioHang(String idnguoidung, String idsach){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("xoa thanh cong 31 "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("loi khi xoa 36"+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idnguoidung", idnguoidung);
                map.put("idsach", idsach);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void setCancelDonHang(String iddonhang){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlcanceldonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("iddonhang", iddonhang);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
