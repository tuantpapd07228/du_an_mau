package com.example.duanmauu.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.Shopping_Activity;
import com.example.duanmauu.adapter.TheLoaiChuaSachAdapter;
import com.example.duanmauu.data.GetIP;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.adapter.SachAdapter;
import com.example.duanmauu.model.TheLoaiSach;
import com.example.duanmauu.model.TheLoaiSoLuong;
import com.example.duanmauu.model.itf.ITFTheLoaiSach;
import com.example.duanmauu.model.itf.SelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SachFragment extends Fragment{

    RecyclerView recyclerView;
    TheLoaiChuaSachAdapter theLoaiChuaSachAdapter;
    ReadData readData;
    ArrayList<TheLoaiSoLuong> arrtheloaisoluong;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sach, container, false);
        arrtheloaisoluong = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleouter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        getdatatheloaisoluong();
        theLoaiChuaSachAdapter = new TheLoaiChuaSachAdapter(arrtheloaisoluong, getContext());
        recyclerView.setAdapter(theLoaiChuaSachAdapter);
        return view;

    }

    public void getdatatheloaisoluong(){
        arrtheloaisoluong = new ArrayList<>();
        readData = new ReadData(getContext());
        readData.getTheLoaiSach(new ITFTheLoaiSach() {
            @Override
            public void getArr(ArrayList<TheLoaiSoLuong> arr1) {
                arrtheloaisoluong.addAll(arr1);
                theLoaiChuaSachAdapter.notifyDataSetChanged();
            }
        });
    }
}