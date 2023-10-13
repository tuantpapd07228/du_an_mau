package com.example.duanmauu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.SachTheoTheLoaiActivity;
import com.example.duanmauu.Shopping_Activity;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.NguoiDung;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.model.TheLoaiSoLuong;
import com.example.duanmauu.model.itf.SelectListener;

import java.util.ArrayList;

public class TheLoaiChuaSachAdapter extends RecyclerView.Adapter<TheLoaiChuaSachAdapter.ViewHolder> {

    private ArrayList<TheLoaiSoLuong> arrtheloaisoluong;
    private ArrayList<Sach> arrsach;
//    private ArrayList<Sach> arrsachtong = new ArrayList<>();
    private SachAdapter adapter;
    NguoiDung nguoiDung;
    ReadData readData;

    Home_Activity context;

    public TheLoaiChuaSachAdapter(ArrayList<TheLoaiSoLuong> arrtheloaisoluong, Context context) {
        this.arrtheloaisoluong = arrtheloaisoluong;
        this.context = (Home_Activity) context;
    }

    @NonNull
    @Override
    public TheLoaiChuaSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iner_recycleview_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiChuaSachAdapter.ViewHolder holder, int position) {
        TheLoaiSoLuong theLoaiSoLuong = arrtheloaisoluong.get(position);
        if (theLoaiSoLuong == null) return;
        readData = new ReadData(context);
        nguoiDung = context.getNguoidung();
        readData.getTheLoaiSachTheoTungChuDe(theLoaiSoLuong.getTen(), new ReadData.XuliSachTheoTheLoai() {
            @Override
            public void xulisachtheotheloai(ArrayList<Sach> arr1) {
                arrsach = new ArrayList<>();
                arrsach.clear();
                arrsach.addAll(arr1);
                System.out.println("sachtheotheloai size arr"+ arrsach.size());
                adapter = new SachAdapter(context, arrsach, new SelectListener() {
                    @Override
                    public void onItemClick(int a) {
                        readData.getTheLoaiSachTheoTungChuDe(arrtheloaisoluong.get(holder.getAdapterPosition()).getTen(), new ReadData.XuliSachTheoTheLoai() {
                            @Override
                            public void xulisachtheotheloai(ArrayList<Sach> arr) {
                                ArrayList<Sach> arrsach2 = new ArrayList<>();
                                arrsach2.addAll(arr);
                                Intent intent = new Intent(context, Shopping_Activity.class);
                                intent.putExtra("sach", arrsach2.get(a));
                                intent.putExtra("nguoidung",nguoiDung);
//                                System.out.println("nguoidung toi day roi"+nguoiDung.getUserName());
                                context.startActivity(intent);
//                                Log.e("size sach toong ", arrsachtong.size()+"");
                            }
                        });

                    }
                });
                adapter.notifyDataSetChanged();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.recyclerViewinner.setLayoutManager(linearLayoutManager);
                holder.recyclerViewinner.setAdapter(adapter);
            }
        });
        holder.tvtheloai.setText(theLoaiSoLuong.getTen());
        holder.viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SachTheoTheLoaiActivity.class);
                Bundle b = new Bundle();
                b.putString("tentheloai", theLoaiSoLuong.getTen());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrtheloaisoluong != null) return arrtheloaisoluong.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewinner;
        LinearLayout linearchuarecyle;
        TextView tvtheloai, viewall;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewinner = itemView.findViewById(R.id.recycleinner);
            tvtheloai = itemView.findViewById(R.id.theloaiinner);
            linearchuarecyle = itemView.findViewById(R.id.linearchuarecyle);
            viewall = itemView.findViewById(R.id.xemtatca);
        }
    }


}
