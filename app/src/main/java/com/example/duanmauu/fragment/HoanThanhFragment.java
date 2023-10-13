package com.example.duanmauu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duanmauu.DonMuaActivity;
import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.adapter.HoaDonAdapter;
import com.example.duanmauu.adapter.HoaDonOuterAdapter;
import com.example.duanmauu.data.DeleteData;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.HoaDonOuter;

import java.util.ArrayList;


public class HoanThanhFragment extends Fragment {

    public ArrayList<HoaDonOuter> arrhoadon;
    RecyclerView recyclerView;
    HoaDonOuterAdapter adapter;
    ReadData readData;
    DonMuaActivity activity;
    DeleteData deleteData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoan_thanh, container, false);
        deleteData = new DeleteData(getContext());
        activity = (DonMuaActivity) getActivity();
        recyclerView = view.findViewById(R.id.hoadonhoanthanhrecycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrhoadon = new ArrayList<>();
        adapter = new HoaDonOuterAdapter(arrhoadon, activity, new HoaDonOuterAdapter.XuLiHuyDon() {
            @Override
            public void xulihuydon(HoaDonOuter hoaDonOuter) {
                deleteData.setCancelDonHang(hoaDonOuter.getIdHoaDon());
            }
        });
        recyclerView.setAdapter(adapter);

        setData();
        return view;
    }

    private void setData(){
        readData = new ReadData(getContext());
        readData.getHoaDon(activity.getNguoidung().getId(), "hoanthanh" ,new ReadData.XuLiHoaDon1() {
            @Override
            public void xulihoadon(ArrayList<HoaDonOuter> arrhoadon1) {
                arrhoadon.addAll(arrhoadon1);
                adapter.notifyDataSetChanged();
            }
        });

    }
}