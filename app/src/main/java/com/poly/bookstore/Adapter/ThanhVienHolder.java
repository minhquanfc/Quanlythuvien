package com.poly.bookstore.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.R;

public class ThanhVienHolder extends RecyclerView.ViewHolder {

    TextView tv_MaTV,tv_tenTV,tv_namSinh;
    ImageView btndelete;
    CardView click;
    LinearLayout linerTV;
    public ThanhVienHolder(@NonNull View itemView) {
        super(itemView);
        tv_MaTV=itemView.findViewById(R.id.tv_maTV);
        tv_tenTV=itemView.findViewById(R.id.tv_tenTV);
        tv_namSinh=itemView.findViewById(R.id.tv_namSinh);
        click=itemView.findViewById(R.id.click);
        btndelete=itemView.findViewById(R.id.btndelete);
        linerTV=itemView.findViewById(R.id.linerTV);
    }
}
