package com.poly.bookstore.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.R;

public class PhieuMuonHolder extends RecyclerView.ViewHolder {
    TextView tv_MaPm, tv_TenTV, tv_TenSach, tv_TienThue, tv_Ngay, Tv_TraSach,tv_tenPm,tv_note;
    ImageView imgDeletePm;
    CardView clickPhieuMuon;
    public PhieuMuonHolder(@NonNull View itemView) {
        super(itemView);
        tv_MaPm=itemView.findViewById(R.id.tv_MaPM);
        tv_TenTV=itemView.findViewById(R.id.tv_TenTV);
        tv_TenSach=itemView.findViewById(R.id.tv_TenSach);
        Tv_TraSach=itemView.findViewById(R.id.tv_TraSach);
        tv_TienThue=itemView.findViewById(R.id.tv_TienThue);
        tv_Ngay=itemView.findViewById(R.id.tv_NgayPM);
        imgDeletePm=itemView.findViewById(R.id.imgDeletePm);
        clickPhieuMuon=itemView.findViewById(R.id.clickPhieuMuon);
        tv_tenPm=itemView.findViewById(R.id.tv_tenPM);
        tv_note=itemView.findViewById(R.id.tv_note);
    }
}
