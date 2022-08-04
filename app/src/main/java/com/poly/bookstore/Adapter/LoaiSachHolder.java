package com.poly.bookstore.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.R;

public class LoaiSachHolder extends RecyclerView.ViewHolder {
    TextView tv_maLoai,tv_tenLoai;
    ImageView btndelete;
    CardView clickLoaiSach;
    public LoaiSachHolder(@NonNull View itemView) {
        super(itemView);
        tv_maLoai=itemView.findViewById(R.id.tv_maLoai);
        tv_tenLoai=itemView.findViewById(R.id.tv_TenLoai);
        btndelete=itemView.findViewById(R.id.imgDeleteLoai);
        clickLoaiSach=itemView.findViewById(R.id.clickLoaiSach);
    }
}
