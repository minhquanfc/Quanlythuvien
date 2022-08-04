package com.poly.bookstore.Adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.R;

public class SachHolder extends RecyclerView.ViewHolder {

    TextView tv_maSach,tv_tenSach,tv_giaThue,tv_maLoai,tv_soTrang;
    ImageView btnxoa;
    CardView clickquanlysach;
    public SachHolder(@NonNull View itemView) {
        super(itemView);
        tv_maSach=itemView.findViewById(R.id.tv_maSach);
        tv_tenSach=itemView.findViewById(R.id.tv_tenSach);
        tv_giaThue=itemView.findViewById(R.id.tv_giaThue);
        tv_maLoai=itemView.findViewById(R.id.tv_maLoaisach);
        btnxoa=itemView.findViewById(R.id.imgdeleteSach);
        clickquanlysach=itemView.findViewById(R.id.clickquanlysach);
        tv_soTrang=itemView.findViewById(R.id.tv_soTrang);
    }
}
