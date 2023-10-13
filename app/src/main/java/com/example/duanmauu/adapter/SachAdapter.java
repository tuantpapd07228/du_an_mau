package com.example.duanmauu.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmauu.R;
import com.example.duanmauu.model.Sach;
import com.example.duanmauu.model.itf.SelectListener;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    private Context context;
    private ArrayList<Sach> arrsach;

    private SelectListener listener;

    public SachAdapter(Context context, ArrayList<Sach> arrsach, SelectListener selectListener) {
        this.context = context;
        this.arrsach = arrsach;
        this.listener = selectListener;
    }

    @NonNull
    @Override
    public SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_sach, parent, false);
        return new SachViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SachViewHolder holder, int position) {
        Sach sach = arrsach.get(position);
        if (sach == null){
            return;
        }
        int id = context.getResources().getIdentifier("drawable/"+sach.getHinh(), null, context.getPackageName());
        holder.imgsach.setImageResource(id);
        holder.nameBook.setText(sach.getTieuDe());
        holder.giagoc.setText(sach.getGiaBan()+30000+"");
        holder.price.setText(sach.getGiaBan()+"");
        holder.soluong.setText(sach.getSoLuong()+"");
        holder.tacgia.setText(sach.getTacGia());
        holder.theloai.setText(sach.getTenTheLoai());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrsach != null){
            return arrsach.size();
        }
        return 0;
    }

    public class SachViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgsach;
        private CardView cardView;
        private TextView giagoc, price, nameBook, soluong, tacgia, theloai;
        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgsach = itemView.findViewById(R.id.sach);
            giagoc = itemView.findViewById(R.id.giagoc);
            giagoc.setPaintFlags(giagoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            price = itemView.findViewById(R.id.price);
            nameBook = itemView.findViewById(R.id.nameBook);
            soluong = itemView.findViewById(R.id.soluong);
            tacgia = itemView.findViewById(R.id.tacgia);
            theloai = itemView.findViewById(R.id.theloairowsach);
        }
    }



}
