package com.poly.bookstore.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.DAO.LoaiSachDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachHolder> implements Filterable{
    Context context;
    List<LoaiSach> loaiSachList;
    LoaiSachDAO dao;

    List<LoaiSach> listOld;

    public LoaiSachAdapter(Context context, List<LoaiSach> loaiSachList) {
        this.context = context;
        this.loaiSachList = loaiSachList;
        dao=new LoaiSachDAO(context);
        this.listOld = loaiSachList;
    }

    @NonNull
    @Override
    public LoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaisach,parent,false);
        return new LoaiSachHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull LoaiSachHolder holder, int position) {
        LoaiSach sach = loaiSachList.get(position);
        holder.tv_maLoai.setText(Integer.toString(sach.getMaLoai()));
        holder.tv_tenLoai.setText(sach.getTenLoai());
//        if (position % 2==0){
//            holder.tv_maLoai.setTypeface(null, Typeface.ITALIC);
//            holder.tv_tenLoai.setTypeface(null, Typeface.ITALIC);
//        } else {
//            holder.tv_maLoai.setTypeface(null, Typeface.BOLD);
//            holder.tv_tenLoai.setTypeface(null, Typeface.BOLD);
//        }
//        if (position ==0){
//            holder.tv_maLoai.setTextColor(Color.BLUE);
//            holder.tv_tenLoai.setTextColor(Color.BLUE);
//        }
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long kq = dao.deleteLoaiSach(String.valueOf(sach.getMaLoai()));
                        if (kq > 0) {
                            loaiSachList.clear();
                            loaiSachList.addAll(dao.danhsachLoaiSach());
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
        holder.clickLoaiSach.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.loai_sach_dialog_update,null);
                builder.setView(view1);

                AlertDialog alertDialog = builder.create();

                EditText ed_tenloai= view1.findViewById(R.id.ed_update_TenLoai);

                Button btnsuaLoaiSach= view1.findViewById(R.id.btnsuaLoaiSach);
                Button btnhuyLoaisach= view1.findViewById(R.id.btnhuyLoaiSach);

                //
                ed_tenloai.setText(sach.getTenLoai());
                //
                btnsuaLoaiSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sach.setTenLoai(ed_tenloai.getText().toString());
                        long kq = dao.updateLoaiSach(sach);
                        if (kq>0){
                            loaiSachList.clear();
                            loaiSachList.addAll(dao.danhsachLoaiSach());
                            notifyItemChanged(position);
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context,"Sửa không thành công",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                btnhuyLoaisach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        return loaiSachList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()){
                    loaiSachList=listOld;
                }else {
                    List<LoaiSach> listnew = new ArrayList<>();
                    for(LoaiSach loaiSach : listOld){
                        if (loaiSach.getTenLoai().toLowerCase().contains(str.toLowerCase())){
                            listnew.add(loaiSach);
                        }
                    }
                    loaiSachList = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = loaiSachList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                loaiSachList = (List<LoaiSach>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
