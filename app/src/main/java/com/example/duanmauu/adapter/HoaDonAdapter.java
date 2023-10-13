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
import com.example.duanmauu.model.HoaDonInner;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    ArrayList<HoaDonInner> arrhoadon;

    Context context;

    public HoaDonAdapter(ArrayList<HoaDonInner> arrhoadon, Context context) {
        this.arrhoadon = arrhoadon;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoaDonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_hoa_don, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {
        HoaDonInner hoaDon = arrhoadon.get(position);
        if (hoaDon==null) {
            return;
        }

        int id = context.getResources().getIdentifier("drawable/"+hoaDon.getHinhAnh(), null, context.getPackageName());
        holder.img.setImageResource(id);
        holder.gia.setText(hoaDon.getTienSach());
        holder.ten.setText(hoaDon.getTieuDe());
        holder.soluong.setText(hoaDon.getSoLuong());
    }

    @Override
    public int getItemCount() {
        if (arrhoadon != null) return arrhoadon.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView ten, gia, soluong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.hoadonimg);
            ten = itemView.findViewById(R.id.hoadontensach);
            gia = itemView.findViewById(R.id.hoadongiatien);
            soluong = itemView.findViewById(R.id.hoadonsoluong);
        }
    }

}
