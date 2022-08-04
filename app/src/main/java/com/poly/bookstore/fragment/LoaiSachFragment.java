package com.poly.bookstore.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.bookstore.Adapter.LoaiSachAdapter;
import com.poly.bookstore.DAO.LoaiSachDAO;
import com.poly.bookstore.MainActivity;
import com.poly.bookstore.R;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.OnSearchListener;
import com.poly.bookstore.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoaiSachFragment extends Fragment implements OnSearchListener {
    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    LoaiSachDAO dao;
    List<LoaiSach> loaiSachList;
    LoaiSachAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_loai_sach,container,false);
        recyclerView=view.findViewById(R.id.rcloaisach);
        actionButton =view.findViewById(R.id.fabloaisach);

        loaiSachList= new ArrayList<>();
        dao= new LoaiSachDAO(getActivity());
        loaiSachList= dao.danhsachLoaiSach();

        adapter= new LoaiSachAdapter(getActivity(),loaiSachList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.loai_sach_item,null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                EditText edmaLoai= view1.findViewById(R.id.ed_maLoai);
                EditText edtenLoai= view1.findViewById(R.id.ed_tenLoai);
                Button btnthem= view1.findViewById(R.id.btnthemloai);
                Button btnhuy= view1.findViewById(R.id.btnhuyloaisach);

                edmaLoai.setEnabled(false);

                btnthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtenLoai.getText().toString().length()==0){
                            Toast.makeText(getActivity(), "Vui lòng nhập tên loại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(edtenLoai.getText().toString().length()<5||edtenLoai.getText().length()>15){
                            Toast.makeText(getActivity(), "Vui lòng nhập từ 5 đến 15 ký tự", Toast.LENGTH_SHORT).show();
                            return;
                        }
//                        Pattern pattern = Pattern.compile("^[0-9]+[a-zA-Z0-9]+$");
//                        Matcher matcher = pattern.matcher(edtenLoai.getText().toString());
//                        if (matcher.find()==false){
//                            Toast.makeText(getActivity(), "Ký tự đầu là số", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                        LoaiSach sach = new LoaiSach();
                        sach.setTenLoai(edtenLoai.getText().toString());
                        long kq = dao.insertLoaiSach(sach);
                        if (kq>0){
                            loaiSachList.clear();
                            loaiSachList.addAll(dao.danhsachLoaiSach());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(),"Thêm không thành công",Toast.LENGTH_LONG).show();
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
            }
        });
        ((MainActivity)getActivity()).setOnSearchListener(this);
        return view;
    }

    @Override
    public void OnSearch(String str) {
        adapter.getFilter().filter(str);
    }
}