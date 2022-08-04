package com.poly.bookstore.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.bookstore.Adapter.ThanhVienAdapter;
import com.poly.bookstore.DAO.ThanhVienDAO;
import com.poly.bookstore.MainActivity;
import com.poly.bookstore.R;
import com.poly.bookstore.model.OnSearchListener;
import com.poly.bookstore.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;


public class ThanhVienFragment extends Fragment implements OnSearchListener {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    List<ThanhVien> thanhVienList;
    ThanhVienAdapter adapter;
    ThanhVienDAO dao;

    EditText edmaTV;
    EditText edtenTV;
    EditText ednamsinh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        recyclerView=view.findViewById(R.id.rc_thanhvien);
        actionButton=view.findViewById(R.id.fab);

        thanhVienList = new ArrayList<>();
        dao=new ThanhVienDAO(getActivity());
        thanhVienList = dao.getAll();

        adapter = new ThanhVienAdapter(getActivity(),thanhVienList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);



        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.thanhvien_dialog,null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                edmaTV= view1.findViewById(R.id.edMaTV);
                edtenTV= view1.findViewById(R.id.edtenTV);
                ednamsinh= view1.findViewById(R.id.ednamSinh);
                Button btnthem= view1.findViewById(R.id.btnthemthanhvien);

                edmaTV.setEnabled(false);

                btnthem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (edtenTV.getText().toString().length()<5 || edtenTV.getText().toString().length()>15){
                                Toast.makeText(getActivity(), "Tên người dùng có độ dài tối thiểu 5, tối đa 15", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else if (edtenTV.getText().toString().equals("")){
                                Toast.makeText(getActivity(), "Tên người dùng không được để trống", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            int namsinh;
                            try {
                                namsinh = Integer.parseInt(ednamsinh.getText().toString());
                            } catch (Exception e){
                                Toast.makeText(getActivity(), "Vui lòng nhập năm sinh là số", Toast.LENGTH_SHORT).show();
                                ednamsinh.requestFocus();
                                return;
                            }

                            ThanhVien thanhVien = new ThanhVien();
                            thanhVien.setHoTen(edtenTV.getText().toString());
                            thanhVien.setNamSinh(ednamsinh.getText().toString());
                            long kq = dao.insertThanhvien(thanhVien);
                            if (kq>0){
                                thanhVienList.clear();
                                thanhVienList.addAll(dao.getAll());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(),"Thêm không thành công",Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
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