package com.example.duanmauu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.R;
import com.example.duanmauu.model.HoaDonInnerXN;


import java.util.ArrayList;

public class DoanhThuInnerAdapter extends RecyclerView.Adapter<DoanhThuInnerAdapter.ViewHolder> {
    ArrayList<HoaDonInnerXN> arr;
    Context context;

    public DoanhThuInnerAdapter(ArrayList<HoaDonInnerXN> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chitiethoadon_inner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDonInnerXN hoadon = arr.get(position);
        if (hoadon == null) return;
        int id  = context.getResources().getIdentifier("drawable/"+hoadon.getHinhAnh(), null, context.getPackageName());
        holder.img.setImageResource(id);
        holder.tensach.setText(hoadon.getTieuDe());
        holder.giaban.setText(hoadon.getTienSach());
        holder.soluong.setText(hoadon.getSoLuong());
    }

    @Override
    public int getItemCount() {
        if (arr != null) return arr.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tensach, giaban, soluong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.doanhthu_img);
            tensach = itemView.findViewById(R.id.doanhthu_tensach);
            giaban = itemView.findViewById(R.id.doanhthu_giaban);
            soluong = itemView.findViewById(R.id.doanhthu_soluong);
        }
    }

}
