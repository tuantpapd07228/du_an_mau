package com.example.duanmauu.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.adapter.DatSachAdapter;
import com.example.duanmauu.data.DeleteData;
import com.example.duanmauu.model.GioHang;
import com.example.duanmauu.adapter.GioHangAdapter;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.data.WriteData;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.model.itf.Cong1;
import com.example.duanmauu.model.itf.ITFGioHang;
import com.example.duanmauu.model.itf.ITFTinhGiaTien;
import com.example.duanmauu.model.itf.Tru1;
import com.example.duanmauu.model.itf.TruTien;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class GioHangFragment extends Fragment {
    View view;
    GioHangAdapter adapter;
    ArrayList<GioHang> arr;
    TextView tongtien;

    Button thanhtoan;
    ReadData readData;
    RecyclerView rec;
    Home_Activity activity;
    DeleteData deleteData;
    NguoiDung nguoiDung;
    WriteData writeData;
    ArrayList<GioHang> arrtinhtien;
    String idhoadon;
    TextView tongtienhang, tongtienthanhtoan, diachi, ten;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_gio_hang, container, false);
        readData = new ReadData(getContext());
        rec = view.findViewById(R.id.recyclegiohang);
        arrtinhtien = new ArrayList<>();
        writeData = new WriteData(getContext());
        activity = (Home_Activity) getActivity();
        nguoiDung = activity.getNguoidung();
        tongtien = view.findViewById(R.id.tonggiatienhang);

        thanhtoan = view.findViewById(R.id.thanhtoan);
        deleteData = new DeleteData(getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        idhoadon = getIdHoaDon();
        rec.setLayoutManager(llm);
        setData();
        rec.setAdapter( adapter );
        return view;
    }


    private void setData(){

        arr = new ArrayList<>();
        try {
            getData();

        }catch (Exception e){
            System.out.println("errrrrrrrrr"+e.getMessage());
        }
        adapter = new GioHangAdapter(getContext(), arr, new GioHangAdapter.XoaKhoiGioHang() {
            @Override
            public void xoakhoigiohang(String idsach) {
                nguoiDung = activity.getNguoidung();
                deleteData.deleteGioHang(nguoiDung.getId(), idsach);
                for (int i = 0; i < arrtinhtien.size(); i++) {
                    if (arrtinhtien.get(i).getIdSach().equals(idsach)){
                        tongtien.setText(setTongTien(arr)- (arrtinhtien.get(i).getGiaTien()*arrtinhtien.get(i).getSoLuongTrongGioHang()) + "");
                        if (arrtinhtien.size() == 0){
                            tongtien.setText(0+"");
                        }
                        System.out.println("tong tien 91 "+ setTongTien(arr));
                        return;
                    }
                }
            }
        }, new ITFTinhGiaTien() {
            @Override
            public void tinhgiatien(GioHang gioHang) {
                // dung
                    arrtinhtien.add(gioHang);
                    tongtien.setText(setTongTien(arrtinhtien)+"");
                if (arrtinhtien.size() == 0){
                    tongtien.setText(0+"");
                }
            }
        }, new Cong1() {
            @Override
            public void cong1(GioHang giohang) {
                writeData.UpdateGioHang(giohang.getIdGHCT(), giohang.getSoLuongTrongGioHang() + 1);
                for (int i = 0; i < arrtinhtien.size(); i++) {
                    if (arrtinhtien.get(i).getIdGHCT().equals(giohang.getIdGHCT())){
                        tongtien.setText(setTongTien(arrtinhtien)+giohang.getGiaTien()+"");
                    }
                }
//                tongtien.setText(setTongTien(arrtinhtien)+ giohang.getGiaTien() + "");
                if (setTongTien(arrtinhtien) <= 0){
                    tongtien.setText(0+"");
                }

            }
        }, new Tru1() {
            @Override
            public void tru1(GioHang gioHang) {
                writeData.UpdateGioHang(gioHang.getIdGHCT(), gioHang.getSoLuongTrongGioHang() );
                System.out.println("update thanh cong");
                tongtien.setText(setTongTien(arrtinhtien)- gioHang.getGiaTien() + "");
                if (setTongTien(arrtinhtien) <= 0){
                    tongtien.setText(0+"");
                }
            }

        }, new TruTien() {
            @Override
            public void trutien(GioHang gioHang, boolean check) {
                // dung
                for (int i = 0; i < arrtinhtien.size(); i++) {
                    if (arrtinhtien.get(i).getIdGHCT().equals(gioHang.getIdGHCT())) {
                        arrtinhtien.remove(i);
                    }
                }
                tongtien.setText(setTongTien(arrtinhtien) + "");
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_bottom);
                Button xacnhan = dialog.findViewById(R.id.dathangxacnhan);
                tongtienhang = dialog.findViewById(R.id.dathangtongtienhang);
                tongtienhang.setText(setTongTien(arrtinhtien)+"");
                ten = dialog.findViewById(R.id.dathangten);
                diachi = dialog.findViewById(R.id.dathangdiachidiachi);
                tongtienthanhtoan = dialog.findViewById(R.id.dathangtongcongtien);
                tongtienthanhtoan.setText(setTongTien(arrtinhtien)+30000+"");
                RecyclerView recyclerView = dialog.findViewById(R.id.recycledathang);
                ten.setText(nguoiDung.getHoten());
                diachi.setText(nguoiDung.getDiachi());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                DatSachAdapter adapter1 = new DatSachAdapter(arrtinhtien, getContext());
                recyclerView.setAdapter(adapter1);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (arrtinhtien.size() > 0){
                        Calendar calendar = new GregorianCalendar();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH);
                        int year = calendar.get(Calendar.YEAR);
                        nguoiDung = activity.getNguoidung();
                        writeData.insertHoaDon(idhoadon, nguoiDung.getId(), year + "/" + month + "/" + day, new WriteData.XuLiHoaDon() {
                            @Override
                            public void xulihoadon(String idhoandon1) {
                                System.out.println("id hoa don 137 "+idhoadon);
                                for (int i = 0; i < arrtinhtien.size(); i++) {
                                    writeData.inserDonHangChiTie(idhoadon, arrtinhtien.get(i).getIdSach(), arrtinhtien.get(i).getSoLuongTrongGioHang());
                                    deleteData.deleteGioHang(nguoiDung.getId(), arrtinhtien.get(i).getIdSach());
                                }
                                arr.clear();
                                arrtinhtien.clear();
                                tongtien.setText(setTongTien(arrtinhtien)+"");
                                getData();
                                adapter1.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Đã xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                        }
                    });
                }

                    }
                });
                try {
                    System.out.println(arrtinhtien.get(0).getIdGHCT()+"");
                        dialog.show();
                }catch (Exception e){
                    Toast.makeText(getContext(), "Bạn chưa chọn sách để thanh toán!", Toast.LENGTH_SHORT).show();

                }
                adapter.notifyDataSetChanged();
//                if (arrtinhtien.size() > 0){
//                    Calendar calendar = new GregorianCalendar();
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);
//                    int month = calendar.get(Calendar.MONTH);
//                    int year = calendar.get(Calendar.YEAR);
//                    nguoiDung = activity.getNguoidung();
//                    writeData.insertHoaDon(idhoadon, nguoiDung.getId(), year + "/" + month + "/" + day, new WriteData.XuLiHoaDon() {
//                        @Override
//                        public void xulihoadon(String idhoandon1) {
//                            System.out.println("id hoa don 137 "+idhoadon);
//                            for (int i = 0; i < arrtinhtien.size(); i++) {
//                                writeData.inserDonHangChiTie(idhoadon, arrtinhtien.get(i).getIdSach(), arrtinhtien.get(i).getSoLuongTrongGioHang());
//                                deleteData.deleteGioHang(nguoiDung.getId(), arrtinhtien.get(i).getIdSach());
//                            }
//                            arr.clear();
//                            arrtinhtien.clear();
//                            tongtien.setText(setTongTien(arrtinhtien)+"");
//                            getData();
//                            adapter.notifyDataSetChanged();
//
//                        }
//                    });
//                }
            }

        });
    }


    private String getIdHoaDon(){
        Random random = new Random();
        int iddonhang = random.nextInt(999999999)+1;
        return iddonhang+"";
    }

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String id = activity.getNguoidung().getId();
                arr.clear();
                readData.getGioHang(id, new ITFGioHang() {
                    @Override
                    public void xuLiGioHang(ArrayList<GioHang> arr1) {
                        arr.addAll(arr1);
                        System.out.println("size aarr"+ arr.size());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        },300);
    }

    private int setTongTien(ArrayList<GioHang> arr){
        int tien = 0;
        for (int i = 0; i < arr.size(); i++) {
            tien = tien + (arr.get(i).getGiaTien() * arr.get(i).getSoLuongTrongGioHang());
        }
        return tien;
    }
}