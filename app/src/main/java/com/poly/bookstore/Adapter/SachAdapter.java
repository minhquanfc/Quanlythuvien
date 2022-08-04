package com.poly.bookstore.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.DAO.LoaiSachDAO;
import com.poly.bookstore.DAO.SachDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachHolder> implements Filterable {
    Context context;
    List<Sach> sachList;
    SachDAO dao;
    LoaiSachDAO loaiSachDAO;
    Sach sach;
    List<Sach> listFull;
    public SachAdapter(Context context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
        dao = new SachDAO(context);
        loaiSachDAO = new LoaiSachDAO(context);
        this.listFull = sachList;
    }

    @NonNull
    @Override
    public SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quan_ly_sach_item,parent,false);
        return new SachHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SachHolder holder, int position) {
        sach =sachList.get(position);
        holder.tv_maSach.setText("Mã sách: "+ sach.getMaSach());
        holder.tv_tenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tv_giaThue.setText("Giá thuê: "+ sach.getGiaThue());

        holder.tv_soTrang.setText("Số trang: "+sach.getSoTrang());
        if (sach.getSoTrang()>1000){
            holder.tv_soTrang.setTypeface(null,Typeface.BOLD);
        }

        LoaiSach loaiSach = loaiSachDAO.getId(String.valueOf(sach.getMaLoai()));
        holder.tv_maLoai.setText("Tên loại: "+loaiSach.getTenLoai());
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long kq = dao.deleteSach(String.valueOf(sach.getMaSach()));
                        if (kq > 0) {
                            sachList.clear();
                            sachList.addAll(dao.danhsachSach());
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
        holder.clickquanlysach.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.sach_dialog_update,null);
                builder.setView(view1);

                AlertDialog alertDialog = builder.create();

                EditText edTenSach= view1.findViewById(R.id.ed_tenSachUpdate);
                EditText edgiaThue= view1.findViewById(R.id.ed_giaThueUpdate);

                EditText edsoTrang= view1.findViewById(R.id.ed_soTrangUpdate);

                Spinner spinner = view1.findViewById(R.id.spinnerQLSUpdate);
                Button btnsua= view1.findViewById(R.id.btnsuaQLsach);
                Button btnhuy= view1.findViewById(R.id.btnhuyQLsach);

                List<LoaiSach> loaiSachList = new ArrayList<>();
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                loaiSachList = loaiSachDAO.danhsachLoaiSach();
                ArrayAdapter arrayAdapter = new ArrayAdapter<LoaiSach>(context, android.R.layout.simple_list_item_1, loaiSachList);
                spinner.setAdapter(arrayAdapter);

                btnsua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sach.setTenSach(edTenSach.getText().toString());
                        sach.setGiaThue(Integer.parseInt(edgiaThue.getText().toString()));
                        sach.setSoTrang(Integer.parseInt(edsoTrang.getText().toString()));
//                        sach.setMaLoai(spinner.getSelectedItem());
                        long kq = dao.updateSach(sach);
                        if (kq>0){
                            sachList.clear();
                            sachList.addAll(dao.danhsachSach());
                            notifyItemChanged(position);
                            Toast.makeText(context,"Sửa thành công",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(context,"Sửa không thành công",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                btnhuy.setOnClickListener(new View.OnClickListener() {
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
        return sachList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()){
                    sachList=listFull;
                } else {
                    List<Sach> listnew=new ArrayList<>();
                    for (Sach sach : listFull){
                        if (String.valueOf(sach.getSoTrang()).toLowerCase().contains(str.toLowerCase())){
                            listnew.add(sach);
                        }
                    }
                    sachList=listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = sachList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sachList = (List<Sach>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
