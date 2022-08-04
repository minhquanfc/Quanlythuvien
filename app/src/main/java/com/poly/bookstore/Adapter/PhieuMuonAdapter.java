package com.poly.bookstore.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.bookstore.DAO.PhieuMuonDAO;
import com.poly.bookstore.DAO.SachDAO;
import com.poly.bookstore.DAO.ThanhVienDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.fragment.PhieuMuonFragment;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.PhieuMuon;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonHolder> implements Filterable {
    Context context;
    List<PhieuMuon> list;
    PhieuMuon phieuMuon;
    List<ThanhVien> thanhVienList;
    List<Sach> listsach;
    PhieuMuonDAO phieuMuonDAO;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    ThanhVien thanhVien;
    Sach sach;
    int masach, tienthue;
    int maThanhVien;
    int positionTV, positionSach;

    List<PhieuMuon> listOld;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        phieuMuonDAO = new PhieuMuonDAO(context);
        sachDAO = new SachDAO(context);
        thanhVienDAO = new ThanhVienDAO(context);
        this.listOld = list;
    }

    @NonNull
    @Override
    public PhieuMuonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phieumuon, null);
        return new PhieuMuonHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PhieuMuonHolder holder, int position) {
        phieuMuon = list.get(position);
        holder.tv_MaPm.setText("Mã phiếu: " + phieuMuon.getMaPM());
        holder.tv_tenPm.setText("Tên phiếu : "+phieuMuon.getTenPM());
        holder.tv_note.setText("Ghi chú: "+phieuMuon.getNote());

        thanhVien = thanhVienDAO.getId(String.valueOf(phieuMuon.getMaTV()));
        holder.tv_TenTV.setText("Thành viên: " + thanhVien.getHoTen());

        sach = sachDAO.getId(String.valueOf(phieuMuon.getMaSach()));
        holder.tv_TenSach.setText("Tên sách: " + sach.getTenSach());

        holder.tv_TienThue.setText("Tiền thuê: " + phieuMuon.getTienThue() + "đ");
        holder.tv_Ngay.setText("Ngày thuê: " + sdf.format(phieuMuon.getNgay()));
        if (phieuMuon.getTraSach() == 1) {
            holder.Tv_TraSach.setTextColor(Color.BLUE);
            holder.Tv_TraSach.setText("Đã trả sách");
        } else {
            holder.Tv_TraSach.setTextColor(Color.RED);
            holder.Tv_TraSach.setText("Chưa trả sách");
        }
        holder.imgDeletePm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn có muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long kq = phieuMuonDAO.deletePm(String.valueOf(phieuMuon.getMaPM()));
                        if (kq > 0) {
                            list.clear();
                            list.addAll(phieuMuonDAO.danhsachPhieuMuon());
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
        holder.clickPhieuMuon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.phieu_muon_dialog_update, null);
                builder.setView(view1);

                AlertDialog alertDialog = builder.create();

                //anh xa
                EditText edmaPm1 = view1.findViewById(R.id.edmaPm1);
                Spinner spTV1 = view1.findViewById(R.id.spMaTV1);
                Spinner spMaSach1 = view1.findViewById(R.id.spMaSach1);
                TextView txtNgaypm1 = view1.findViewById(R.id.txtNgaypm1);
                TextView txtTienthuepm1 = view1.findViewById(R.id.txtTienthuepm1);
                CheckBox chkTraSach1 = view1.findViewById(R.id.chkTraSach1);
                Button btnUpdatePm = view1.findViewById(R.id.btnUpdatePm);
                Button btnHuyUpdatePm = view1.findViewById(R.id.btnHuyUpdatePm);


                //do du lieu spinner thanh vien
                thanhVienList = new ArrayList<>();
                thanhVienList = thanhVienDAO.getAll();
                ArrayAdapter spinnerTVadapter = new ArrayAdapter<ThanhVien>(context, android.R.layout.simple_list_item_1, thanhVienList);
                spTV1.setAdapter(spinnerTVadapter);

                spTV1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maThanhVien = thanhVienList.get(position).maTV;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //do du lieu spinner ma sach
                listsach = new ArrayList<>();
                listsach = sachDAO.danhsachSach();
                ArrayAdapter spinnerMSadapter = new ArrayAdapter<Sach>(context, android.R.layout.simple_list_item_1, listsach);
                spMaSach1.setAdapter(spinnerMSadapter);

                spMaSach1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        masach = listsach.get(position).maSach;
                        tienthue = listsach.get(position).giaThue;
                        txtTienthuepm1.setText("Tiền thuê : " + tienthue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                // do du lieu len dialog
                edmaPm1.setEnabled(false);
                edmaPm1.setText(String.valueOf(phieuMuon.getMaPM()));
                for (int i = 0; i < thanhVienList.size(); i++)
                    if (phieuMuon.getMaTV() == (thanhVienList.get(i).getMaTV())) {
                        positionTV = i;
                    }
                spTV1.setSelection(positionTV);

                for (int i = 0; i < listsach.size(); i++)
                    if (phieuMuon.getMaTV() == (listsach.get(i).getMaSach())) {
                        positionSach = i;
                    }
                spMaSach1.setSelection(positionSach);

                txtNgaypm1.setText("Ngày thuê : " + sdf.format(phieuMuon.getNgay()));
                txtTienthuepm1.setText("Tiền thuê : " + phieuMuon.getTienThue());
                if (phieuMuon.getTraSach() == 1) {
                    chkTraSach1.setChecked(true);
                } else {
                    chkTraSach1.setChecked(false);
                }

                //update

                btnUpdatePm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        phieuMuon.setMaSach(masach);
                        phieuMuon.setMaTV(maThanhVien);
                        phieuMuon.setNgay(new Date());
                        phieuMuon.setTienThue(tienthue);
                        if (chkTraSach1.isChecked()) {
                            phieuMuon.setTraSach(1);
                        } else {
                            phieuMuon.setTraSach(0);
                        }
                        long kq = phieuMuonDAO.updatePm(phieuMuon);
                        if (kq > 0) {
                            list.clear();
                            list.addAll(phieuMuonDAO.danhsachPhieuMuon());
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Sửa không thành công", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });


                btnHuyUpdatePm.setOnClickListener(new View.OnClickListener() {
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
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str = constraint.toString();
                if (str.isEmpty()){
                    list=listOld;
                }else {
                    List<PhieuMuon> listnew = new ArrayList<>();
                    for(PhieuMuon phieuMuon : listOld){
                        if (String.valueOf(phieuMuon.getTenPM()).toLowerCase().contains(str.toLowerCase())){
                            listnew.add(phieuMuon);
                        }
                    }
                    list = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<PhieuMuon>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
