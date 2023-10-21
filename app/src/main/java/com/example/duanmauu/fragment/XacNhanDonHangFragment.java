package com.example.duanmauu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duanmauu.DonMuaActivity;
import com.example.duanmauu.R;
import com.example.duanmauu.adapter.XacNhanDonHanAdapter;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.HoaDonOuterXN;
import com.example.duanmauu.model.NguoiDung;

import java.util.ArrayList;


public class XacNhanDonHangFragment extends Fragment {
    ArrayList<HoaDonOuterXN> arrouter;
    ReadData readData;
    LinearLayoutManager linearLayoutManager;
    XacNhanDonHanAdapter adapter;
    RecyclerView recyclerView;
    DonMuaActivity donMuaActivity;
    NguoiDung nguoiDung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_xac_nhan_don_hang, container, false);
        recyclerView = view.findViewById(R.id.xacnhandonhangrecycleview);
        readData = new ReadData(getContext());
        donMuaActivity = (DonMuaActivity) getActivity();
        nguoiDung = donMuaActivity.getNguoidung();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        readData.getHoaDonDoiXacNhan(nguoiDung.getId() ,new ReadData.XuLiHoaDonDoiXacNhan() {
            @Override
            public void xulihoadondoixacnhan(ArrayList<HoaDonOuterXN> arrayList) {
                try {
                    arrouter = new ArrayList<>();
                    arrouter.addAll(arrayList);
                    adapter = new XacNhanDonHanAdapter(arrouter, getContext());
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    Toast.makeText(donMuaActivity, "errrr 54 "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}