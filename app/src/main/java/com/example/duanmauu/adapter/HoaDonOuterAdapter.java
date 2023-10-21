package com.example.duanmauu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.DonMuaActivity;
import com.example.duanmauu.Home_Activity;
import com.example.duanmauu.R;
import com.example.duanmauu.data.ReadData;
import com.example.duanmauu.model.HoaDonInner;
import com.example.duanmauu.model.HoaDonInnerXN;
import com.example.duanmauu.model.HoaDonOuter;
import com.example.duanmauu.model.HoaDonOuterXN;


import java.util.ArrayList;

public class HoaDonOuterAdapter extends RecyclerView.Adapter<HoaDonOuterAdapter.ViewHolder> {

     ArrayList<HoaDonOuter> arrouter;
     ArrayList<HoaDonInner> arrinner;
     HoaDonAdapter adapter;
    ReadData readData;
    DonMuaActivity context;
    public interface XuLiHuyDon{void xulihuydon(HoaDonOuter hoaDonOuter);}
    XuLiHuyDon xuLiHuyDon;

    public HoaDonOuterAdapter(ArrayList<HoaDonOuter> arrouter, DonMuaActivity context, XuLiHuyDon xuLiHuyDon) {
        this.arrouter = arrouter;
        this.context = context;
        this.xuLiHuyDon = xuLiHuyDon;
        readData = new ReadData(context);
    }

    @NonNull
    @Override
    public HoaDonOuterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_hoadon_outer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonOuterAdapter.ViewHolder holder, int position) {
        HoaDonOuter hoaDonOuter = arrouter.get(position);
        if (hoaDonOuter == null ) return;
        holder.idhoadon.setText(hoaDonOuter.getIdHoaDon());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.tongtien.setText(Integer.parseInt(hoaDonOuter.getTongTien())+30000+"");
        holder.ngaymua.setText(hoaDonOuter.getNgayMua());
        if (hoaDonOuter.getTrangthai().equals("hoanthanh")){
            holder.trangthai.setText("Hoàn thành");
        } else if (hoaDonOuter.getTrangthai().equals("huy") ) {
            holder.trangthai.setText("Đã hủy");
        }else if (hoaDonOuter.getTrangthai().equals("vanchuyen")){
            holder.trangthai.setText("Vận chuyển");
        }else {
            holder.cancel.setVisibility(View.VISIBLE);
            holder.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    xuLiHuyDon.xulihuydon(hoaDonOuter);
                    holder.cancel.setVisibility(View.INVISIBLE);
                    holder.trangthai.setText("Đã hủy");
                }
            });
            holder.trangthai.setText("Đợi xác nhận");
        }
        readData.getHoaDoninner(hoaDonOuter.getIdHoaDon(), new ReadData.XuLiHoaDonInner1() {
            @Override
            public void xulihoadoninner(ArrayList<HoaDonInner> arrhoadon1) {
                arrinner = new ArrayList<>();
                arrinner.addAll(arrhoadon1);
                adapter = new HoaDonAdapter(arrinner, context);
                holder.recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arrouter != null ) return  arrouter.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView idhoadon, tongtien, ngaymua, trangthai;
        Button cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.hoadonouterrecycleview);
            idhoadon = itemView.findViewById(R.id.hoadonouteridhoadon);
            tongtien = itemView.findViewById(R.id.hoadonoutertongtien);
            ngaymua = itemView.findViewById(R.id.hoadonouterngaymua);
            trangthai = itemView.findViewById(R.id.hoadonoutertrangthai);
            cancel = itemView.findViewById(R.id.hoadonoutercancel);
        }
    }
}
