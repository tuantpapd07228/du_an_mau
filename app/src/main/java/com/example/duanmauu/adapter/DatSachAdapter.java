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
import com.example.duanmauu.model.GioHang;

import java.util.List;

public class DatSachAdapter extends RecyclerView.Adapter<DatSachAdapter.ViewHolder> {

    List<GioHang> arrgiohang;
    Context context;


    public DatSachAdapter(List<GioHang> arrgiohang, Context context) {
        this.arrgiohang = arrgiohang;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DatSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dat_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatSachAdapter.ViewHolder holder, int position) {
        GioHang giohang = arrgiohang.get(position);
        int id = context.getResources().getIdentifier("drawable/"+giohang.getHinhAnh(), null, context.getPackageName());
        holder.img.setImageResource(id);
        holder.giaban.setText(giohang.getGiaTien()+"");
        holder.ten.setText(giohang.getTenSach());
        holder.soluong.setText(giohang.getSoLuongTrongGioHang()+"");
    }
    private int tinhgiatien(int giatien , int soluong){
        int tongtien =0;
        tongtien = giatien * soluong;
        return tongtien;
    }

    @Override
    public int getItemCount() {
        if (arrgiohang != null) return arrgiohang.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten, giaban, soluong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.datsachimg);
            ten = itemView.findViewById(R.id.datsachten);
            giaban = itemView.findViewById(R.id.datsachgiaban);
            soluong = itemView.findViewById(R.id.datsachsoluong);

        }
    }
}
