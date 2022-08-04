package com.poly.bookstore.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.poly.bookstore.DAO.ThanhVienDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.model.ThanhVien;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienHolder> implements Filterable {
    Context context;
    List<ThanhVien> thanhVienList;
    ThanhVienDAO dao;

    List<ThanhVien> listOld;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList) {
        this.context = context;
        this.thanhVienList = thanhVienList;
        dao = new ThanhVienDAO(context);
        this.listOld = thanhVienList;
    }

    @NonNull
    @Override
    public ThanhVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thanhvien_item, parent, false);
        return new ThanhVienHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ThanhVienHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        holder.tv_MaTV.setText("Mã TV: " + String.valueOf(thanhVien.getMaTV()));
        holder.tv_tenTV.setText("Tên TV: " + thanhVien.getHoTen());
        holder.tv_namSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
//        if (position % 2==0){
//            holder.tv_MaTV.setTextColor(Color.RED);
////            holder.linerTV.setBackgroundColor(Color.BLUE); //set nen
//        }
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long kq = dao.deleteThanhvien(String.valueOf(thanhVien.getMaTV()));
                        if (kq > 0) {
                            thanhVienList.clear();
                            thanhVienList.addAll(dao.getAll());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        holder.click.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.update_thanh_vien_dialog, null);
                builder.setView(view1);

                AlertDialog alertDialog = builder.create();

//                EditText edmaTV= view1.findViewById(R.id.edMaTV);
                EditText edtenTV = view1.findViewById(R.id.edtenTV);
                EditText ednamsinh = view1.findViewById(R.id.ednamSinh);
                Button btnsua = view1.findViewById(R.id.btnsuathanhvien);
                Button btnhuy = view1.findViewById(R.id.btnhuythanhvien);

                //
                edtenTV.setText(thanhVien.getHoTen());
                ednamsinh.setText(thanhVien.getNamSinh());


                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thanhVien.setHoTen(edtenTV.getText().toString());
                        thanhVien.setNamSinh(ednamsinh.getText().toString());
                        long kq = dao.updateThanhvien(thanhVien);
                        if (kq > 0) {
                            thanhVienList.clear();
                            thanhVienList.addAll(dao.getAll());
                            notifyItemChanged(position);
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtenTV.setText("");
                        ednamsinh.setText("");
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()){
                    thanhVienList=listOld;
                }else {
                    List<ThanhVien> listnew = new ArrayList<>();
                    for(ThanhVien thanhVien : listOld){
                        if (thanhVien.getHoTen().toLowerCase().contains(str.toLowerCase())){
                            listnew.add(thanhVien);
                        }
                    }
                    thanhVienList = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = thanhVienList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                thanhVienList = (List<ThanhVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
