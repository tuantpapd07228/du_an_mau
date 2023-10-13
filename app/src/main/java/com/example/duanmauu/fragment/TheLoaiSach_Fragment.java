package com.example.duanmauu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.SachTheoTheLoaiActivity;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.adapter.TheLoaiSachAdapter;
import com.example.duanmauu.model.TheLoaiSoLuong;
import com.example.duanmauu.model.itf.ITFTheLoaiSach;

import java.util.ArrayList;


public class TheLoaiSach_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private ReadData readData;

    public ArrayList<TheLoaiSoLuong> arr;
    public static TheLoaiSachAdapter adapter;
    private Home_Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_loai_sach_, container, false);
        activity = (Home_Activity) getActivity();
        readData = new ReadData(getContext());
        recyclerView = view.findViewById(R.id.rclview);

        arr = new ArrayList<>();
        adapter = new TheLoaiSachAdapter(arr, getContext(), new TheLoaiSachAdapter.XuLiKhiChonSachTheoTheLoai() {
            @Override
            public void xulisachthetheloai(String idtheloai) {
                Intent intent = new Intent(getContext(), SachTheoTheLoaiActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("nguoidung",activity.getNguoidung());
                b.putString("tentheloai", idtheloai);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        getdata();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    public void getdata(){
           readData.getTheLoaiSach(new ITFTheLoaiSach() {
               @Override
               public void getArr(ArrayList<TheLoaiSoLuong> arr1) {
                   arr.addAll(arr1);
                   adapter.notifyDataSetChanged();
               }
           });
    }


}