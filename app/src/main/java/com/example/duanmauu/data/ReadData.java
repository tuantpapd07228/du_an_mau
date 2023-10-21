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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duanmauu.model.GioHang;
import com.example.duanmauu.model.HoaDonInner;
import com.example.duanmauu.model.HoaDonInnerXN;
import com.example.duanmauu.model.HoaDonOuter;
import com.example.duanmauu.model.HoaDonOuterXN;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.model.TheLoaiSoLuong;
import com.example.duanmauu.model.itf.ITFGioHang;
import com.example.duanmauu.model.itf.ITFTheLoaiSach;
import com.example.duanmauu.model.itf.SelectNguoidung;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadData {
    Context context;
    String urlnguoidung= GetIP.IP + ":8686/duanmau/get_nguoidung.php";
    String urlsach=GetIP.IP + ":8686/duanmau/get_sach.php";
    String urlhoadoninner= GetIP.IP +":8686/duanmau/get_HoaDoninner.php";
    String urlhoadonouter= GetIP.IP +":8686/duanmau/get_HoaDonOuter.php";
    String urltheloaisachtheotungchude = GetIP.IP +":8686/duanmau/get_theloaisachtheotungchude.php";
    String urltheloaisachtheotungchudelimit15 = GetIP.IP +":8686/duanmau/get_theloaisachtheotungchudelimit15.php";
    String urlgiohang=GetIP.IP +":8686/duanmau/get_giohang.php";
    String urltheloai= GetIP.IP + ":8686/duanmau/get_TheLoaiSach.php";
    String urlhoadondoixacnhan = GetIP.IP +":8686/duanmau/donhang_doixacnhan.php";
    String urlhoadondoixacnhan1 = GetIP.IP+":8686/duanmau/get_donhangdangchoxacnhan.php";

    String urldonhangdangvanchuyen = GetIP.IP+":8686/quanly/get_donhangdangvanchuyen.php";
    String urlhoadondahoanthanh = GetIP.IP+":8686/duanmau/get_donhangdahoanthanh.php";
    String urlhoadonvanchuyen = GetIP.IP+":8686/duanmau/get_hoadonvanchuyen.php";

    ArrayList<NguoiDung> arrnguoidung = new ArrayList<>();
    SelectNguoidung selectNguoidung;


    public ReadData(Context context) {
        this.context = context;
    }


    // ghi list người dùng
    public ArrayList<NguoiDung> getNguoiDung(){
        ArrayList<NguoiDung> arrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlnguoidung, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrayList.add( new NguoiDung(
                                        jsonObject.getString("IDnguoidung"),
                                        jsonObject.getString("Username"),
                                        jsonObject.getString("Password"),
                                        jsonObject.getString("Sdt"),
                                        jsonObject.getString("Hoten"),
                                        jsonObject.getString("Diachi"),
                                        jsonObject.getString("Email")
                                ));
                            } catch (JSONException e) {
                                Log.e("EEEEEEEEE", e.getMessage());
                            }
                        }
//                        selectNguoidung.selectNguoidung(arrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EEEEEEEEEEEEEE", error.getMessage());
            }
        });

        requestQueue.add(jsonArrayRequest);
        return arrayList;
    }
    // get 1 nguoi dung

    public void getNguoiDung1(String username, SelectNguoidung selectNguoidung){
        String url = GetIP.IP +":8686/duanmau/get_userpw.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);
                    JSONObject jsonObject = j.getJSONObject(0);
                    NguoiDung nguoiDung = new NguoiDung(
                            jsonObject.getString("IDnguoidung"),
                            jsonObject.getString("Username"),
                            jsonObject.getString("Password"),
                            jsonObject.getString("Sdt"),
                            jsonObject.getString("Hoten"),
                            jsonObject.getString("Diachi"),
                            jsonObject.getString("Email")
                    );
                    selectNguoidung.selectNguoidung(nguoiDung);
                } catch (JSONException e) {
                    Log.e("ERRRRRRRRRRR ghi nguoidung 1", e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERRRRRRRRRRR ghi nguoidung 2", error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    // ghi list sách
    public interface XuLiSauKhiLayDataSach{void xulisaukhilaydatasach(ArrayList<Sach> arr);}
    public void getSach(XuLiSauKhiLayDataSach xuLiSauKhiLayDataSach){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlsach, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Sach> arr = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Sach sach = new Sach(
                                jsonObject.getString("IDSach"),
                                jsonObject.getString("MaTheLoai"),
                                jsonObject.getString("TieuDe"),
                                jsonObject.getString("TacGia"),
                                jsonObject.getString("NXB"),
                                jsonObject.getString("MoTa"),
                                jsonObject.getInt("GiaBan"),
                                jsonObject.getInt("SoLuong"),
                                jsonObject.getString("LinkHinhAnh"),
                                jsonObject.getString("tenTheLoai"));
                        arr.add(sach);
                    } catch (JSONException e) {
                        Log.e("ERRRRRRRRR Sach 1",e.getMessage());
                    }
                }
                xuLiSauKhiLayDataSach.xulisaukhilaydatasach(arr);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERRRRRRRRR Sach read 162 ",error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    // ghi líst hóa đơn outer
    public interface XuLiHoaDon1{void xulihoadon(ArrayList<HoaDonOuter> arrhoadon);}
    public void getHoaDon(String idnguoidung, String trangthai ,XuLiHoaDon1 xuLiHoaDon1 ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadonouter, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<HoaDonOuter> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arr.add(new HoaDonOuter(
                                jsonObject.getString("idHoaDon"),
                                jsonObject.getString("ngayMua"),
                                jsonObject.getString("tongTien"),
                                jsonObject.getString("trangThai")));
                    }

                    xuLiHoaDon1.xulihoadon(arr);
                } catch (JSONException e) {
                   Log.e("errr read 197 ",e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errr read 207 ",error.getMessage() );
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idnguoidung", idnguoidung);
                map.put("trangthai", trangthai);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }


    //ghi list hoa don inner
    public interface XuLiHoaDonInner1 {void xulihoadoninner(ArrayList<HoaDonInner> arrhoadon1);}
    public void getHoaDoninner(String idhoadon , XuLiHoaDonInner1 xuLiHoaDon2 ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadoninner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<HoaDonInner> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arr.add(new HoaDonInner(
                                jsonObject.getString("tieuDe"),
                                jsonObject.getString("soLuongMua"),
                                jsonObject.getString("tienSach"),
                                jsonObject.getString("hinhAnh")));
                    }
                    xuLiHoaDon2.xulihoadoninner(arr);
                } catch (JSONException e) {
                    Log.e("errr read 236 ",e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errr read 242 ",error.getMessage() );
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idhoadon", idhoadon);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    public interface XuLiHoaDonInner2 {void xulihoadoninner(ArrayList<HoaDonInnerXN> arrhoadon1);}
    public void getHoaDoninner(String idhoadon , XuLiHoaDonInner2 xuLiHoaDon2 ){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadoninner, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<HoaDonInnerXN> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        arr.add(new HoaDonInnerXN(
                                jsonObject.getString("tieuDe"),
                                jsonObject.getString("soLuongMua"),
                                jsonObject.getString("tienSach"),
                                jsonObject.getString("hinhAnh")));
                    }
                    xuLiHoaDon2.xulihoadoninner(arr);
                } catch (JSONException e) {
                    Log.e("errr read 236 ",e.getMessage() );
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errr read 242 ",error.getMessage() );
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idhoadon", idhoadon);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    public interface XuLiHoaDonDoiXacNhan{void xulihoadondoixacnhan(ArrayList<HoaDonOuterXN > arrayList);}
    public void getHoaDonDoiXacNhan(String id ,XuLiHoaDonDoiXacNhan xuLiHoaDondoixacnhan){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadondoixacnhan1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<HoaDonOuterXN> arrayList = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            arrayList.add(new HoaDonOuterXN(
                                    jsonObject.getString("idHoaDon"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("ngayMua"),
                                    jsonObject.getString("tongTien"),
                                    jsonObject.getString("ten"),
                                    jsonObject.getString("sdt"),
                                    jsonObject.getString("diachi")));

                    }
                } catch (JSONException e) {
                    Toast.makeText(context, "errrrrrr 324 readdata" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                xuLiHoaDondoixacnhan.xulihoadondoixacnhan(arrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "errrrrrr329 readdata" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("idnguoidung", id);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
    //ghi líst hóa đơn chi tiết
//    public void getHoaDonChiTiet(){
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urldonhangchitiet, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                ArrayList<HoaDonChiTiet> arr = new ArrayList<>();
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        HoaDonChiTiet hdct = new HoaDonChiTiet(
//                            jsonObject.getString("IDHDCT"),
//                                jsonObject.getString("IDHoaDon"),
//                                jsonObject.getString("IDSach"),
//                                jsonObject.getInt("SoluongMua")
//                        );
//                        arr.add(hdct);
//                    } catch (JSONException e) {
//                        Log.e("ERRRRRRRRR hoadonchitiet 1",e.getMessage());
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("ERRRRRRRRR hoadonchitiet 2",error.getMessage());
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//
//    }
    public interface XuLiHoaDonVanChuyen1{void xulihoadonvanchuyen(ArrayList<HoaDonOuterXN > arrayList);}
    public void getHoaDonVanChuyen(String id ,XuLiHoaDonVanChuyen1 xuLiHoaDondvanchuyen){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
       StringRequest stringRequest = new StringRequest(Request.Method.POST, urlhoadonvanchuyen, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               ArrayList<HoaDonOuterXN> arrayList = new ArrayList<>();
               try {
                   JSONArray jsonArray = new JSONArray(response);
                   for (int i = 0; i < jsonArray.length(); i++) {
                       JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                       arrayList.add(new HoaDonOuterXN(
                               jsonObject.getString("idHoaDon"),
                               jsonObject.getString("username"),
                               jsonObject.getString("ngayMua"),
                               jsonObject.getString("tongTien"),
                               jsonObject.getString("ten"),
                               jsonObject.getString("sdt"),
                               jsonObject.getString("diachi")));

                   }
               } catch (JSONException e) {
                   throw new RuntimeException(e);
               }
               xuLiHoaDondvanchuyen.xulihoadonvanchuyen(arrayList);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       }){
           @Nullable
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               Map<String , String> map = new HashMap<>();
               map.put("idnguoidung", id);
               return map;
           }
       };
       requestQueue.add(stringRequest);
    }
    // ghi list giỏ hàng
    public void getGioHangByIDGHCT( String idghct , String id, ITFGioHang xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<GioHang> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GioHang gioHang = new GioHang(
                                //       idHDCT, String idSach, String idNguoiDung, String tenSach,String hinhAnh, int soLuong, int giaTien, int soLuongCuaSach
                                jsonObject.getString("IdGH"),
                                jsonObject.getString("IdSach"),
                                jsonObject.getString("IdNguoiDung"),
                                jsonObject.getString("TenSach"),
                                jsonObject.getString("HinhAnh"),
                                jsonObject.getInt("SoLuong"),
                                jsonObject.getInt("GiaTien"),
                                jsonObject.getInt("SoLuongSach")
                        );
                        arr.add(gioHang);
                    }
                    xuli.xuLiGioHang(arr);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("loi o 268 read"+ error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map= new HashMap<>();
                map.put("idnguoidung",id);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }
    // ghi list giỏ hàng
    public void getGioHang(String id, ITFGioHang xuli){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlgiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<GioHang> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GioHang gioHang = new GioHang(
                                //       idHDCT, String idSach, String idNguoiDung, String tenSach,String hinhAnh, int soLuong, int giaTien, int soLuongCuaSach
                                jsonObject.getString("IdGH"),
                                jsonObject.getString("IdSach"),
                                jsonObject.getString("IdNguoiDung"),
                                jsonObject.getString("TenSach"),
                                jsonObject.getString("HinhAnh"),
                                jsonObject.getInt("SoLuong"),
                                jsonObject.getInt("GiaTien"),
                                jsonObject.getInt("SoLuongSach")
                        );
                        arr.add(gioHang);
                    }
                    xuli.xuLiGioHang(arr);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("loi o 268 read"+ error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map= new HashMap<>();
                map.put("idnguoidung",id);
                return map;
            }
        };
        requestQueue.add(stringRequest);

    }

    // ghi list thể loại sách va so luong
    public void getTheLoaiSach(ITFTheLoaiSach itfTheLoaiSach){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urltheloai, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<TheLoaiSoLuong> arr = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        TheLoaiSoLuong t = new TheLoaiSoLuong(
                                jsonObject.getString("tenTheLoai"),
                                jsonObject.getInt("soLuong")
                        );
                        arr.add(t);

                    } catch (JSONException e) {
                        Log.e("ERRRRRRRRR the loai sach 1",e.getMessage());
                    }
                }
                itfTheLoaiSach.getArr(arr);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERRRRRRRRR theloaisach 2",error.getMessage());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    // ghi the loai sach theo tung chu de
    public interface XuliSachTheoTheLoai {void xulisachtheotheloai (ArrayList<Sach> arr);    }
    public void getTheLoaiSachTheoTungChuDe(String tentheloai, XuliSachTheoTheLoai xuliSachTheoTheLoai){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltheloaisachtheotungchude, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Sach> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Sach sach = new Sach(
                                jsonObject.getString("IDSach"),
                                jsonObject.getString("MaTheLoai"),
                                jsonObject.getString("TieuDe"),
                                jsonObject.getString("TacGia"),
                                jsonObject.getString("NXB"),
                                jsonObject.getString("MoTa"),
                                jsonObject.getInt("GiaBan"),
                                jsonObject.getInt("SoLuong"),
                                jsonObject.getString("LinkHinhAnh"),
                                jsonObject.getString("tenTheLoai")
                        );
                        arr.add(sach);
                    }
                    xuliSachTheoTheLoai.xulisachtheotheloai(arr);

                } catch (JSONException e) {
                    Log.e("read er 341 ", arr.size()+"");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("read er 348"+ error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tentheloai", tentheloai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getTheLoaiSachTheoTungChuDeLimit15(String tentheloai, XuliSachTheoTheLoai xuliSachTheoTheLoai){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urltheloaisachtheotungchudelimit15, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Sach> arr = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Sach sach = new Sach(
                                jsonObject.getString("IDSach"),
                                jsonObject.getString("MaTheLoai"),
                                jsonObject.getString("TieuDe"),
                                jsonObject.getString("TacGia"),
                                jsonObject.getString("NXB"),
                                jsonObject.getString("MoTa"),
                                jsonObject.getInt("GiaBan"),
                                jsonObject.getInt("SoLuong"),
                                jsonObject.getString("LinkHinhAnh"),
                                jsonObject.getString("tenTheLoai")
                        );
                        arr.add(sach);
                        Log.e("er 338 ", arr.size()+"");
                    }
                    xuliSachTheoTheLoai.xulisachtheotheloai(arr);

                } catch (JSONException e) {
                    System.out.println("read 316"+ e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("read 348"+ error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("tentheloai", tentheloai);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
