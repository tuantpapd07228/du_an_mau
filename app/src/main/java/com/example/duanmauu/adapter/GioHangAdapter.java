package com.example.duanmauu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.R;
import com.example.duanmauu.model.GioHang;
import com.example.duanmauu.model.itf.Cong1;
import com.example.duanmauu.model.itf.ITFTinhGiaTien;
import com.example.duanmauu.model.itf.SetSoLuong;
import com.example.duanmauu.model.itf.Tru1;
import com.example.duanmauu.model.itf.TruTien;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    Context context;
    ArrayList<GioHang> arr;
    SetSoLuong xoa;
    ITFTinhGiaTien tinhtien;
    Cong1 cong;
    Tru1 tru;
    TruTien truTien;
    public interface XoaKhoiGioHang{void xoakhoigiohang(String idsach);}
    XoaKhoiGioHang xoaKhoiGioHang;
    boolean check = false;

    public GioHangAdapter(Context context, ArrayList<GioHang> arr, XoaKhoiGioHang xoaKhoiGioHang, ITFTinhGiaTien tinhtien, Cong1 cong, Tru1 tru, TruTien truTien) {
        this.context = context;
        this.arr = arr;
        this.xoaKhoiGioHang = xoaKhoiGioHang;
        this.tinhtien = tinhtien;
        this.cong = cong;
        this.tru = tru;
        this.truTien = truTien;
    }

    @NonNull
    @Override
    public GioHangAdapter.GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_gio_hang, parent, false);
        return new GioHangViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.GioHangViewHolder holder, int position) {
        GioHang gioHang = arr.get(position);
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggg 57 --"+gioHang.getSoLuongTrongGioHang());
        if (gioHang == null) return;
        int hinh = context.getResources().getIdentifier(gioHang.getHinhAnh(), null, context.getPackageName());
        holder.img.setImageResource(hinh);
        holder.tensach.setText(gioHang.getTenSach());
        try {
            int id = context.getResources().getIdentifier("drawable/"+gioHang.getHinhAnh(), null, context.getPackageName());
            holder.soluong.setText(gioHang.getSoLuongTrongGioHang()+"");
            holder.soluongtrongkho.setText(gioHang.getSoLuong()+"");
            holder.giatien.setText((gioHang.getSoLuongTrongGioHang() * gioHang.getGiaTien())+"");
            holder.img.setImageResource(id);
            holder.tru1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((gioHang.getSoLuongTrongGioHang() - 1) <1 ){
                        xoaKhoiGioHang.xoakhoigiohang(gioHang.getIdSach());
                        for (int i = 0; i < arr.size(); i++) {
                            if (arr.get(i).getIdSach().equals(gioHang.getIdSach())){
                                arr.remove(i);
                            }
                        }
                        notifyDataSetChanged();
                    }else{
                        tru.tru1(gioHang);
                        holder.soluong.setText(gioHang.getSoLuongTrongGioHang()-1+"");
                        gioHang.setSoLuongTrongGioHang(gioHang.getSoLuongTrongGioHang()-1);
                        holder.giatien.setText(tinhgiatien(gioHang.getGiaTien(), gioHang.getSoLuongTrongGioHang())+"");
                    }

                }
            });
            holder.cong1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gioHang.getSoLuongTrongGioHang() >= gioHang.getSoLuong()){
                        gioHang.setSoLuongTrongGioHang(gioHang.getSoLuong()) ;
                    }
                    if (check){
                        cong.cong1(gioHang);

                    }

                    holder.soluong.setText(gioHang.getSoLuongTrongGioHang()+1+"");
                    gioHang.setSoLuongTrongGioHang(gioHang.getSoLuongTrongGioHang()+1);
                    holder.giatien.setText(tinhgiatien(gioHang.getGiaTien(), gioHang.getSoLuongTrongGioHang())+"");
                }
            });
            holder.chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        check = true;
                        tinhtien.tinhgiatien(gioHang);
                    }else{
                        check = false;
                        truTien.trutien(gioHang, false);
                        System.out.println("checked = falseeeeeeeeeeeeeeeeeeee");
                    }
                }
            });

        }catch (Exception e){
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+e.getMessage());
        }
    }

    private int tinhgiatien(int giatien , int soluong){
        int tongtien =0;
        tongtien = giatien * soluong;
        return tongtien;
    }

    @Override
    public int getItemCount() {
        if (arr != null){
            return arr.size();
        }
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        ImageView img, cong1, tru1;
        TextView tensach, giatien, soluong, soluongtrongkho;
        CheckBox chk;
        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.sachgiohang);
            tensach = itemView.findViewById(R.id.tensachgiohang);
            giatien = itemView.findViewById(R.id.sotongtiencuasachgiohang);
            soluong = itemView.findViewById(R.id.soluonggiohang);
            chk = itemView.findViewById(R.id.chbgiohang);
            cong1 = itemView.findViewById(R.id.cong1giohang);
            tru1 = itemView.findViewById(R.id.tru1giohang);
            soluongtrongkho = itemView.findViewById(R.id.soluongtrongkho);
        }
    }
}
