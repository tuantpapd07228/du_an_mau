package com.example.duanmauu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.DonMuaActivity;
import com.example.duanmauu.R;
import com.example.duanmauu.adapter.VanChuyenAdapter;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.HoaDonOuter;
import com.example.duanmauu.model.HoaDonOuterXN;
import com.example.duanmauu.model.NguoiDung;


import java.util.ArrayList;


public class VanChuyenFragment extends Fragment {

    VanChuyenAdapter adapter;
    ArrayList<HoaDonOuterXN> arrouter;
    RecyclerView recyclerView;
    ReadData readData;
    DonMuaActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_van_chuyen, container, false);
        arrouter = new ArrayList<>();
        activity = (DonMuaActivity) getActivity();
        NguoiDung nguoiDung = activity.getNguoidung();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView = view.findViewById(R.id.vanchuyenrecyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        readData = new ReadData(getContext());
        readData.getHoaDonVanChuyen(nguoiDung.getId(), new ReadData.XuLiHoaDonVanChuyen1() {
            @Override
            public void xulihoadonvanchuyen(ArrayList<HoaDonOuterXN> arrayList) {
                arrouter.addAll(arrayList);
                adapter = new VanChuyenAdapter(arrouter, getContext());
                recyclerView.setAdapter(adapter);
            }

//            @Override
//            public void xulidoanhthu(ArrayList<HoaDonOuterXN> arhoadondoanhthu) {
//
//            }
        });
        return view;
    }
}